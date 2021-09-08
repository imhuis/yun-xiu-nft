package com.tencent.nft.core.jpa;

import com.tencent.nft.domain.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author: imhuis
 * @date: 2020/1/28
 * @description:
 */
@Component
public class AuditorAwareImpl implements AuditorAware<Long> {

    final private Logger logger = LoggerFactory.getLogger(AuditorAwareImpl.class);

    @Override
    public Optional<Long> getCurrentAuditor() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            return Optional.of(user.getId());
        }catch (ClassCastException e){
            logger.info(e.getMessage());
            return Optional.empty();
        }

    }
}
