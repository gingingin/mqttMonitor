package com.yzwy.configure;

/*

*/
/**
 * 后台应用，不考虑集群需求，使用guava作为cache
 *//*

//@Configuration
//@ConfigurationProperties(prefix = "spring.cache.guava")
//@EnableCaching(proxyTargetClass = true)
public class CacheConfig {

    private String spec;

//    @Bean
    protected CacheManager cacheManager() {
        GuavaCacheManager cacheManager = new GuavaCacheManager();
        cacheManager.setCacheBuilder(CacheBuilder.from(spec));
        return cacheManager;
    }


    public void setSpec(String spec) {
        this.spec = spec;
    }
}
*/
