> 使用方法

在spring方法上添加 [EnableHttp.java](..%2F..%2Fdian1-spring-boot-autoconfigure%2Fhttp-spring-boot-autoconfigure%2Fsrc%2Fmain%2Fjava%2Fcom%2Fdian1%2Fhttp%2Fannotate%2FEnableHttp.java)
注解并添加扫描路径即可启动

---------

> 案例

导入maven

```xml

<dependency>
   <groupId>io.github.poo0054</groupId>
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
    *
    * @return 返回strng
    */
   @Get("/m1/2406035-0-default/omsProductDetail/get")
   @Auth("123456789")
   String get();

   @Get("m1/2406035-0-default/omsProductDetail/getProductCode/{code}")
   String restful(@BasicAuth Map nu, @Restful("code") String code);

   @Get("m1/2406035-0-default/omsProductHeader/get")
   void form(String id, Consumer consumer, @Auth("key") Map key);

   @Post("/m1/2406035-0-default/omsContractHead/list")
   @BasicAuth(username = "abc", password = "asd")
   OmsContractHead basicAuth(Map map);

   @Post("/m1/2406035-0-default/omsContractHead/list")
   @Header({"Authorization: 123456789", "Accept-Encoding: gzip"})
   OmsContractHead header();

   @Post("/m1/2406035-0-default/omsContractHead/list")
   OmsContractHead headerPar(String str);

   @Post("/m1/2406035-0-default/omsContractHead/list")
   OmsContractHead headerMap(@Header Map str);

   @Post("https://oms.test.1-dian.cn/oms/gen/download/1637748183482265601")
   @Header("Authorization: Bearer 318e7574-8e33-4a00-8e64-beeb15eb1ce3")
   HttpResponse dowFile(@Download File file);

   @Post("https://oms.test.1-dian.cn/oms/gen/download/1637748183482265601")
   @Header("Authorization: Bearer 318e7574-8e33-4a00-8e64-beeb15eb1ce3")
   HttpResponse dowOutputStream(@Download OutputStream outputStream, StreamProgress streamProgress);

   @Post("http://127.0.0.1:4523/m1/2406035-0-default/common/templateUploadFile")
   void uploadMap(Map<String, Object> map);

   @Post("test")
   void upload(@Form("file") File file);

   @Post("https://mock.apifox.cn/m1/2406035-0-default/omsProductDetail/add?apifoxToken=byFyzN6aEZfSNoiLWuoaBc7dvPtTlWo8")
   void validate(@HttpValidated Request request, @Header("apifoxToken") String hean);
}

/**
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

   @GetMapping("restful/{code}")
   public String test(@PathVariable("code") String code) {
      Map<Object, Object> map = new HashMap<>();
      map.put("username", "张三");
      map.put("password", "123456");
      return httpTest.restful(map, code);
   }

   @GetMapping("form/{code}")
   public String getOmsProductHeader(@PathVariable("code") String code) {
      AtomicReference<String> body = new AtomicReference<>();
      Consumer<HttpResponse> consumer = response -> {
         System.out.println();
         System.out.println();
         body.set(response.body());
      };
      Map<Object, Object> map = new HashMap<>();
      map.put("key", "123456789");
      httpTest.form(code, consumer, map);
      return body.get();
   }

   @GetMapping("body/{code}")
   public OmsContractHead form(@PathVariable("code") String code) {
      HashMap<String, Object> map = new HashMap<>();
      map.put("size", 1);
      map.put("start", 1);
      map.put("productCode", code);
      OmsContractHead body = httpTest.basicAuth(map);
      return body;
   }

   @GetMapping("header/{code}")
   public OmsContractHead header(@PathVariable("code") String code) {
      OmsContractHead head = httpTest.header();
      return head;
   }

   @GetMapping("headerPar/{code}")
   public OmsContractHead headerPar(@PathVariable("code") String code) {
      OmsContractHead head = httpTest.headerPar("asd");
      return head;
   }

   @GetMapping("headerMap/{code}")
   public OmsContractHead headerMap(@PathVariable("code") String code) {
      Map<Object, Object> hashMap = new HashMap<>();
      hashMap.put("Authorization", "123456789");
      hashMap.put("Accept", " application/json");
      OmsContractHead head = httpTest.headerMap(hashMap);
      return head;
   }

   @GetMapping("dow")
   public void dow(HttpServletResponse response) throws IOException {
      response.reset();
      response.setHeader("Content-Disposition", "attachment; filename=\"poo0054.zip\"");
      response.setContentType("application/octet-stream; charset=UTF-8");
      StreamProgress streamProgress = new StreamProgress() {
         @Override
         public void start() {
            System.out.println("我开始了");
         }

         @Override
         public void progress(long progressSize) {
            //如果需要获取全部大小 从response 的 Content-Length获取总大小
            System.out.println("当前传了" + progressSize);
         }

         @Override
         public void finish() {
            System.out.println("结束了");
         }
      };
      HttpResponse httpResponse = httpTest.dowOutputStream(response.getOutputStream(), streamProgress);
      System.out.println(httpResponse);
   }

   @GetMapping("dowFile")
   public void dowFile() throws IOException {
      File file = new File("D:/test.zip");
      if (file.isFile()) {
         file.createNewFile();
      }
      HttpResponse httpResponse = httpTest.dowFile(file);
      System.out.println(httpResponse);
   }

   @GetMapping("upload")
   public void upload() throws IOException {
      File file = new File("D:/test.zip");
      if (file.isFile()) {
         file.createNewFile();
      }
      httpTest.upload(file);
   }

   @GetMapping("uploadMap")
   public void uploadMap() throws IOException {
      File file = new File("D:/test.zip");
      if (file.isFile()) {
         file.createNewFile();
      }
      Map<String, Object> map = new HashMap<>();
      map.put("file", file);
      httpTest.uploadMap(map);
   }

   @GetMapping("validate")
   public void validate() {
      Request request = new Request();
      OmsContractHead omsContractHead = new OmsContractHead();
      request.setExpenseAccountCode("asd");
      request.setOmsContractHead(omsContractHead);
      omsContractHead.setStart(1);
      String hean = "byFyzN6aEZfSNoiLWuoaBc7dvPtTlWo8";
      httpTest.validate(request, hean);
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