package cn.lhzs.common.support.image;

import cn.lhzs.common.config.Resources;

/**
 * 统一设置图片FTP地址
 * 
 * @author sonic.liu
 * @version 2016年12月10日 17时50分
 */
public final class ImageUtil {
	public static final String IMAGE_URL = Resources.IMAGE.getString("image.url");

	/** 默认图片地址 */
	public static final String DEF_IMG_URL = IMAGE_URL + Resources.IMAGE.getString("image.def");

	/** 默认头像图片地址 */
	public static final String DEF_AVATAR_URL = IMAGE_URL + Resources.IMAGE.getString("image.avatar");
}
