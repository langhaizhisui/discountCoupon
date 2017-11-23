//package cn.lhzs.common.support.oss;
//
//import cn.lhzs.common.config.Resources;
//import cn.lhzs.common.exception.FtpException;
//import com.aliyun.oss.ClientException;
//import com.aliyun.oss.OSSClient;
//import com.aliyun.oss.OSSException;
//import com.aliyun.oss.model.ObjectMetadata;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.*;
//
//import static java.util.Objects.nonNull;
//
///**
// * OSS 文件上传工具类，单例，通过getInstanse获取对象
// *
// * @author chenzhm
// */
//public final class OSSUploader {
//
//    public static int IMG_MAX_SIZE = 1024 * 350;
//    private Logger log = LoggerFactory.getLogger(OSSUploader.class);
//
//    private OSSClient client;
//    private String accessId;
//    private String secret;
//    private String endPoint;
//    // 图片服务器保存文件目录
//    private String bucketName;
//    // 缩略图宽度
//    private int litimgWidth;
//    // 缩略图高度
//    private int litimgHeight;
//
//    //使用静态内部类加载对象，使用classloder的机制来保证初始化instance时只有一个实例
//    private static class SingletonHolder {
//        private static final OSSUploader INSTANCE = new OSSUploader();
//    }
//
//    public static OSSUploader getInstanse() {
//        return SingletonHolder.INSTANCE;
//    }
//
//    private OSSUploader() {
//        endPoint = Resources.IMAGE.getString("ibalife.album_url");
//        accessId = Resources.IMAGE.getString("oss.key.id");
//        secret = Resources.IMAGE.getString("oss.key.secret");
//        bucketName = Resources.IMAGE.getString("oss.bucket.name");
//        client = new OSSClient(endPoint, accessId, secret);
//
//        litimgWidth = Integer.parseInt(Resources.IMAGE.getString("ibalife.pic.litimg.width"));
//        litimgHeight = Integer.parseInt(Resources.IMAGE.getString("ibalife.pic.litimg.hight"));
//    }
//
//    /**
//     * 上传图片至OSS
//     *
//     * @param image    java.awt.image.BufferedImage类型的图片
//     * @param fileName 图片名称
//     * @return
//     * @throws IOException
//     */
//    private boolean putImg(BufferedImage image, String fileName, String mimeType) throws IOException {
//        String imgeType = getFileType(fileName);
//        if (imgeType == null) return false;
//
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        ImageIO.write(image, imgeType.toUpperCase(), bos);
//        try (ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray())) {
//            // 创建上传Object的Metadata
//            ObjectMetadata meta = new ObjectMetadata();
//            // 必须设置ContentLength
//            meta.setContentLength(bis.available());
//            meta.setContentType(mimeType);
//            if (client.doesObjectExist(bucketName, fileName)) {
//                return false;
//            }
//            // 上传Object.
//            client.putObject(bucketName, fileName, bis, meta);
//            return true;
//        } catch (IOException e) {
//            throw e;
//        } finally {
//            try {
//                bos.close();
//            } catch (Exception e) {
//            }
//        }
//    }
//
//    /**
//     * 删除对象
//     *
//     * @param objName
//     * @return
//     */
//    public boolean deleteObj(String objName) {
//        client.deleteObject(bucketName, objName);
//        return true;
//    }
//
//    /**
//     * 在文件名称的尾部追加字符串
//     *
//     * @param fileName 原始的文件名
//     * @param appendix 需要在文件名后面追加的字符串
//     * @return 追加字符串后的文件名
//     */
//    private String lpadFileName(String fileName, String appendix) {
//        String[] splits = fileName.split("\\.");
//        // 文件名称不包含后缀,直接在文件名后面加上追加的字符串
//        if (splits.length < 2) {
//            return fileName + appendix;
//        }
//        // 文件名包含后，截取后綴前面的字符串，拼接追加字符串，再拼接后缀
//        StringBuilder tmp = new StringBuilder();
//        for (int i = 0; i < splits.length - 1; i++) {
//            tmp.append(splits[i]).append(".");
//        }
//        tmp = new StringBuilder(tmp.substring(0, tmp.length() - 1));
//        tmp.append(appendix).append(".");
//        tmp.append(splits[splits.length - 1]);
//        return tmp.toString();
//    }
//
//    /**
//     * 获取文件名后缀
//     *
//     * @param fileName
//     * @return
//     */
//    private String getFileType(String fileName) {
//        String[] splits = fileName.split("\\.");
//        // 文件名称不包含后缀,直接在文件名后面加上追加的字符串
//        if (splits.length < 2) {
//            return null;
//        }
//        return splits[splits.length - 1];
//    }
//
//
//    /**
//     * 创建目录
//     *
//     * @param folderName 目录名称
//     * @return
//     */
//    public boolean createFolder(String folderName) {
//        byte[] buffer = new byte[0];
//        ObjectMetadata objectMeta;
//        objectMeta = new ObjectMetadata();
//        objectMeta.setContentLength(0);
//        try (ByteArrayInputStream in = new ByteArrayInputStream(buffer)) {
//            client.putObject(bucketName, folderName, in, objectMeta);
//            log.info("创建目录成功。bucket(" + bucketName + ") objKey(" + folderName + ")");
//        } catch (IOException | OSSException e) {
//            return false;
//        } catch (ClientException e) {
//            log.error("阿里云服务发送请求时出现的错误，或客户端无法处理返回结果", e);
//            return false;
//        }
//
//        return true;
//    }
//
//    /**
//     * 判断对象是否存在
//     *
//     * @param objKey OSS中对象保存的key
//     * @return
//     */
//    public boolean isObjExist(String objKey) {
//        try {
//            client.doesObjectExist(bucketName, objKey);
//        } catch (OSSException e) {
//            return false;
//        } catch (ClientException e) {
//            log.error("阿里云服务发送请求时出现的错误，或客户端无法处理返回结果", e);
//            return false;
//        }
//        return true;
//    }
//
//    public boolean putObj(String key, InputStream is) {
//        try {
//            // 创建上传Object的Metadata
//            ObjectMetadata meta = new ObjectMetadata();
//            // 必须设置ContentLength
//            meta.setContentLength(is.available());
//            if (client.doesObjectExist(bucketName, key)) {
//                return false;
//            }
//            // 上传Object.
//            client.putObject(bucketName, key, is, meta);
//            log.info("文件上传成功，bucketName(" + bucketName + ") objKey(" + key + ")");
//            return true;
//        } catch (IOException e) {
//            log.error("读取文件流失败", e);
//            return false;
//        }
//    }
//
//    /**
//     * JPG图片上传
//     */
//    public String putJpg(String key, InputStream is, boolean needLitimg, String mimeType) throws Exception {
//        try {
//            if (is.available() > IMG_MAX_SIZE) {
//                throw new FtpException("上传图片超过限制");
//            }
//
////			JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(is);
////			BufferedImage image = decoder.decodeAsBufferedImage();
////			putImg(image, key, mimeType);
//            putImg(is, key, mimeType);
//            log.info("图片上传成功。 bucketName(" + bucketName + ") imageName(" + key + ")");
//            if (!needLitimg) {
//                return key;
//            }
////			int targetWidth = litimgWidth;
////			int targetHeight = litimgHeight;
////			BufferedImage compressedImage = null;
////			if (image.isAlphaPremultiplied()) {
////				compressedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TRANSLUCENT);
////			} else {
////				compressedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
////			}
////			Graphics2D graph = compressedImage.createGraphics();
////			graph.drawImage(image, 0, 0, targetWidth, targetHeight, null);
////			String suffix = StringUtil.combineString("_", litimgWidth + "", "*", litimgHeight + "");
////			String litimgKey = lpadFileName(key, suffix);
////			putImg(compressedImage, litimgKey, mimeType);
////			log.info("缩略图上传成功。bucketName(" + bucketName + ") litimgName(" + litimgKey + ")");
//            return key;
//        } catch (FtpException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new FtpException("图片损坏无法上传", e);
//        }
//    }
//
//    private boolean putImg(InputStream inputStream, String fileName, String mimeType) throws IOException {
//        String imgeType = getFileType(fileName);
//        if (imgeType == null) return false;
//
//        ByteArrayOutputStream bos = null;
//        ByteArrayInputStream bis = null;
//        try {
//            byte[] buffer = new byte[1024];
//            int count;
//            bos = new ByteArrayOutputStream();
//
//            while ((count = inputStream.read(buffer)) != -1) {
//                bos.write(buffer, 0, count);
//            }
//            // Flush out stream, to write any remaining buffered data
//            bos.flush();
//
//            bis = new ByteArrayInputStream(bos.toByteArray());
//            // 创建上传Object的Metadata
//            ObjectMetadata meta = new ObjectMetadata();
//            // 必须设置ContentLength
//            meta.setContentLength(bis.available());
//            meta.setContentType(mimeType);
//            if (client.doesObjectExist(bucketName, fileName)) {
//                return false;
//            }
//            // 上传Object.
//            client.putObject(bucketName, fileName, bis, meta);
//            return true;
//        } catch (IOException e) {
//            throw e;
//        } finally {
//            try {if(nonNull(bos)) bos.close();} catch (Exception e) {}
//            try {if(nonNull(bis)) bis.close();} catch (Exception e) {}
//        }
//    }
//
//    public static void main(String[] args) throws IOException {
//        BufferedImage bi = ImageIO.read(new File("C:\\Users\\Administrator\\Desktop\\test.jpg"));
//        String[] propNames = bi.getPropertyNames();
//        if (null == propNames) {
//            return;
//        }
//        for (String name : propNames) {
//            System.out.println(name);
//        }
//    }
//}
