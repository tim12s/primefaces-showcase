package org.primefaces.showcase.util;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import org.primefaces.cache.CacheProvider;
import org.primefaces.cache.EHCacheProvider;

/**
 * Skip the usage via PrimeApplicationContext, it's not available in 6.2.x
 */
@ApplicationScoped
public class ShowcaseCacheProvider {
    private CacheProvider cacheProvider;

    @PostConstruct
    public void init() {
        cacheProvider = new EHCacheProvider();
    }
    
    public CacheProvider getCacheProvider() {
        return cacheProvider;
    }
}
