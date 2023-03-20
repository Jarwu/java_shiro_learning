`shiro` source code, after importing the source code, `idea` enters from `shiro/samples/web`

```shell
git clone https://github.com/apache/shiro.git
cd shiro
git checkout shiro-root-1.2.4
```
Edit `pom.xml` in the `shiro/samples/web` directory, and change the version of `jstl` to `1.2`. There is no version by default, and an error will be reported when parsing.
```xml
<dependency>  
    <groupId>javax.servlet</groupId>  
    <artifactId>jstl</artifactId>  
    <version>1.2</version>  
    <scope>runtime</scope>  
</dependency>
```

The python encryption script is placed at https://github.com/Jarwu/decrypt_for_shiro550