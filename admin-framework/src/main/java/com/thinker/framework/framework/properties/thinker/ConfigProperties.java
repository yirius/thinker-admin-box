package com.thinker.framework.framework.properties.thinker;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ConfigProperties {
    private String loginUrl;
    private List<String> xssNames;
    private Map<String, String> resourcePaths;
    private String allowedOrigins;
    private String uploadPath;
    private List<String> uploadImageSuffix;
    private long thumbImageSize;
    private long uploadImageSize;
    private List<String> uploadFileSuffix;
    private long uploadFileSize;
    private String rabbitExchange;
    private String rabbitQueueDefault;
    private String rabbitQueueOrder;
    private Integer rabbitRetry;
    private Integer rabbitRetryDelay;
}
