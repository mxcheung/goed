package au.com.goed.starter.web.filter;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

/**
 * <p>
 * An implementation of CommonsRequestLoggingFilter but allows to select whether to disable pre request or
 * post request as opposed to always logging both.
 * </p>
 * <p>
 * Unlike the CommonsRequestLoggingFilter log level needs to be set to INFO =>
 * logging.level.au.com.goed.starter.web.filter.RequestLoggingFilter=INFO
 * </p>
 * <p>
 * To set log mode set property logging.request.filter.mode to be either ALL|PRE|POST
 * </p>
 * 
 * @author Goed Bezig
 */
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {

    protected static final String LOG_ALL = "ALL";
    protected static final String LOG_PRE_REQUEST = "PRE";
    protected static final String LOG_POST_REQUEST = "POST";

    private static final String REGEX = String.format("\\b(?:%s|%s|%s)\\b", LOG_ALL, LOG_PRE_REQUEST, LOG_POST_REQUEST);
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    @Value("${logging.request.filter.mode:ALL}")
    private String mode;

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return PATTERN.matcher(mode).find();
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        if (LOG_ALL.equals(mode) || LOG_PRE_REQUEST.equals(mode)) {
            logger.info(message);
        }
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        if (LOG_ALL.equals(mode) || LOG_POST_REQUEST.equals(mode)) {
            logger.info(message);
        }
    }
}
