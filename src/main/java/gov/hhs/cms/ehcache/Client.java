package gov.hhs.cms.ehcache;

import com.google.common.base.Optional;
import com.google.common.collect.RangeSet;
import gov.hhs.cms.base.common.cache.FEPSCacheBase;
import gov.hhs.cms.base.common.cache.refcode.RefcodeCacheWrapper;
import org.apache.log4j.Logger;
import org.apache.regexp.RE;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Set;

/**
 * Created by dev on 5/20/16.
 */
@Produces("application/json")
public class Client {

    private static final Logger logger = Logger.getLogger(Client.class);
    private static Optional<FEPSCacheBase> cacheBase;
    @GET
    @Path("loadCache")
    public Response loadCache() {
        logger.debug("Received to rest call to Load Cache");
        cacheBase = RefcodeCacheWrapper.getCache(true);
        logger.debug("Received FEPSCacheBase cache optional instance, initialized :" + cacheBase.isPresent());
        return Response.status(Response.Status.OK).entity("Cache initialized Successfully").build();
    }

    @GET
    @Path("checkCache")
    public Response checkCache(){
        Boolean enabled = RefcodeCacheWrapper.isCacheEnabled(true);
        logger.debug("Received rest call to check if cache is enabled");
        if (enabled){
            logger.debug("Cache Enabled");
            return Response.status(Response.Status.OK).entity("Cache enabled").build();
        }
        else{
            logger.debug("Cache NOT Enabled");
            return Response.status(Response.Status.OK).entity("Cache NOT enabled").build();
        }
    }

    @GET
    @Path("cacheSize")
    public Response cacheSize(){
        Set cacheSet = cacheBase.asSet();
        int cacheSetSize = cacheSet.size();
        logger.debug("CacheBase size is " + cacheSetSize);

        while (cacheSet.iterator().hasNext()){
            logger.debug(cacheSet.iterator().next());

        }
        return  Response.status(Response.Status.OK).entity("CacheBase size is " + cacheSetSize).build();



    }
}
