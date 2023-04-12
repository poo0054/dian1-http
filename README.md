> 使用方法

在spring方法上添加
[EnableHttp.java](..%2F..%2Fdian1-spring-boot-autoconfigure%2Fhttp-spring-boot-autoconfigure%2Fsrc%2Fmain%2Fjava%2Fcom%2Fdian1%2Fhttp%2Fannotate%2FEnableHttp.java)
注解并添加扫描路径即可启动(未添加路径不会注入spring中)

---------

> 案例

导入maven

```xml

<dependency>
   <groupId>com.dian1</groupId>
   <artifactId>http-spring-boot-starter</artifactId>
   <version>version</version>
</dependency>
```

使用案例

```java

@OpenHttp("127.0.0.1:4523")
public interface HttpTest {

   /**
    * get方法
    * <p>
    * auth: 请求头 Authorization 的值  类似 Authorization: 123456789
    */
   @Get("/m1/2406035-0-default/omsProductDetail/get")
   @Auth("123456789")
   String get();

   /**
    * 普通授权,根据username:password 进行base64编译,构建后类似   Authorization: Basic YWxhZGRpbjpvcGVuc2VzYW1l
    */
   @Post("/m1/2406035-0-default/omsContractHead/list")
   @BasicAuth(username = "abc", password = "asd")
   OmsContractHead basicAuth(@Form Map map);

   /**
    * restfull风格替换
    * BasicAuth:参数上,值为map. 会根据注解的username找到map的key.注解的password找到value再进行base64编译
    */
   @Get("m1/2406035-0-default/omsProductDetail/getProductCode/{code}")
   String restful(@BasicAuth Map nu, @Restful("code") String code);

   /**
    * 参数的方式添加form 必须添加启动参数 -parameters
    *
    * @Auth :header对应map=key:value
    */
   @Get("m1/2406035-0-default/omsProductHeader/get")
   void form(String id, Consumer consumer, @Auth("key") Map key);

   /**
    * Header: 请求头信息
    */
   @Post("/m1/2406035-0-default/omsContractHead/list")
   @Header({"Authorization: 123456789", "Accept-Encoding: gzip"})
   OmsContractHead header();

   /**
    * 返回值默认fastjson格式化 .适配复杂的结构
    */
   @Post("/m1/2406035-0-default/omsContractHead/list")
   OmsContractHead headerPar(String str);

   /**
    * Header:对应map的key:value
    */
   @Post("/m1/2406035-0-default/omsContractHead/list")
   OmsContractHead headerMap(@Header Map str);

   /**
    * 文件下载
    * Header: 使用:分割
    */
   @Post("https://oms.test.1-dian.cn/oms/gen/download/1637748183482265601")
   @Header("Authorization: Bearer 318e7574-8e33-4a00-8e64-beeb15eb1ce3")
   HttpResponse dowFile(File file);

   /**
    * 文件下载
    */
   @Post("https://oms.test.1-dian.cn/oms/gen/download/1637748183482265601")
   @Header("Authorization: Bearer 318e7574-8e33-4a00-8e64-beeb15eb1ce3")
   HttpResponse dowOutputStream(OutputStream outputStream, StreamProgress streamProgress);

   /**
    * 文件上传:  会自动构建请求头.value为file即可
    *
    * @param map
    */
   @Post("http://127.0.0.1:4523/m1/2406035-0-default/common/templateUploadFile")
   void uploadMap(@Form Map<String, Object> map);

   /**
    * 文件上传:  会自动构建请求头.
    * key:key  value :file
    */
   @Post("test")
   void upload(@Form("key") File file);

   /**
    * Header: key为apifoxToken , value为header
    */
   @Post("https://mock.apifox.cn/m1/2406035-0-default/omsProductDetail/add?apifoxToken=byFyzN6aEZfSNoiLWuoaBc7dvPtTlWo8")
   void validate(@HttpValidated Request request, @Header("apifoxToken") String header);
}


/**
 * 使用方法,@Autowired注入即可
 * @author zhangzhi
 * @date 2023/3/28
 */
@RestController
public class TestController {

   @Autowired
   HttpTest httpTest;

   @GetMapping
   public String get() {
      return httpTest.get();
   }

}


```

---------

> 自定义

该框架最大特点可以任意自定义.可以使用嵌套注解做成一个组合注解.

例如:

```java

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@BasicAuth(username = "", password = "")
@Header(value = "Accept: text/html,application/json,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
@Delete("/delete")
public @interface Custom {

}
```

自定义注解类(必须注入spring才能生效):
[ClassHandle.java](..%2F..%2Fdian1-spring-boot-autoconfigure%2Fhttp-spring-boot-autoconfigure%2Fsrc%2Fmain%2Fjava%2Fcom%2Fdian1%2Fhttp%2Fhandle%2Fbase%2FClassHandle.java)
[TypeHandle.java](..%2F..%2Fdian1-spring-boot-autoconfigure%2Fhttp-spring-boot-autoconfigure%2Fsrc%2Fmain%2Fjava%2Fcom%2Fdian1%2Fhttp%2Fhandle%2Ftype%2FTypeHandle.java)
[ParameterHandle.java](..%2F..%2Fdian1-spring-boot-autoconfigure%2Fhttp-spring-boot-autoconfigure%2Fsrc%2Fmain%2Fjava%2Fcom%2Fdian1%2Fhttp%2Fhandle%2Fparameter%2FParameterHandle.java)
[BuildHttpRequest.java](http-spring-boot-autoconfigure%2Fsrc%2Fmain%2Fjava%2Fcom%2Fdian1%2Fhttp%2Fbuild%2FBuildHttpRequest.java)

1. ClassHandle对应的是最上层接口.对应
   [ClassHandle.java](http-spring-boot-autoconfigure%2Fsrc%2Fmain%2Fjava%2Fcom%2Fdian1%2Fhttp%2Fhandle%2FClassHandle.java)
2. TypeHandle对应的是接口上的注解(method的注解)例如
   [GetHandle.java](http-spring-boot-autoconfigure%2Fsrc%2Fmain%2Fjava%2Fcom%2Fdian1%2Fhttp%2Fhandle%2Fmethod%2FGetHandle.java)
   和
   [PostHandle.java](http-spring-boot-autoconfigure%2Fsrc%2Fmain%2Fjava%2Fcom%2Fdian1%2Fhttp%2Fhandle%2Fmethod%2FPostHandle.java)
3. ParameterHandle对的是参数注解.
   例如
   [FormHandle.java](http-spring-boot-autoconfigure%2Fsrc%2Fmain%2Fjava%2Fcom%2Fdian1%2Fhttp%2Fhandle%2Fparameter%2FFormHandle.java)
   和
   [BodyHandle.java](http-spring-boot-autoconfigure%2Fsrc%2Fmain%2Fjava%2Fcom%2Fdian1%2Fhttp%2Fhandle%2Fparameter%2FBodyHandle.java)
4. BuildHttpRequest构建请求和返回响应
   [HutoolBuildHttpRequest.java](http-spring-boot-autoconfigure%2Fsrc%2Fmain%2Fjava%2Fcom%2Fdian1%2Fhttp%2Fbuild%2FHutoolBuildHttpRequest.java)

---------


> 基本原理

根据
[EnableHttp.java](http-spring-boot-autoconfigure%2Fsrc%2Fmain%2Fjava%2Fcom%2Fdian1%2Fhttp%2Fannotate%2FEnableHttp.java)
路径扫描所有存在
[OpenHttp.java](http-spring-boot-autoconfigure%2Fsrc%2Fmain%2Fjava%2Fcom%2Fdian1%2Fhttp%2Fannotate%2FOpenHttp.java)
的注解注入 spring的FactoryBean , 并创建代理对象执行

---------