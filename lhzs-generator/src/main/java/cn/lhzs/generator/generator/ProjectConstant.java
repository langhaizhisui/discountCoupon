package cn.lhzs.generator.generator;

/**
 * 项目常量
 */
public interface ProjectConstant {
     String BASE_PACKAGE = "com.iba.drp";//项目基础包名称，根据自己公司的项目修改

     String MODEL_PACKAGE = BASE_PACKAGE + ".model";//Model所在包
     String MAPPER_PACKAGE = BASE_PACKAGE + ".mapper";//Mapper所在包
     String SERVICE_PACKAGE = BASE_PACKAGE + ".service";//Service所在包
     String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";//ServiceImpl所在包
     String CONTROLLER_PACKAGE = BASE_PACKAGE + ".web";//Controller所在包

     String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".base.Mapper";//Mapper插件基础接口的完全限定名
}
