package com.tencent.security.core.security.oauth.provider;

import com.tencent.security.repository.oauth.ClientDetailsDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

/**
 * @author: imhuis
 * @date: 2021/9/1
 * @description:
 */
//@Service
public class OauthClientDetailsService implements ClientDetailsService {

    private ClientDetailsDao clientDetailsDao;

    public OauthClientDetailsService(ClientDetailsDao clientDetailsDao) {
        this.clientDetailsDao = clientDetailsDao;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetails details;
        try {
            details = clientDetailsDao.findByClientId(clientId);
        }
        catch (EmptyResultDataAccessException e) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
        return details;
    }
}
