package com.tencent.security.repository.oauth;

import com.tencent.security.domain.security.oauth.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ClientDetailsDao extends JpaRepository<OauthClientDetails,String> {

    OauthClientDetails findByClientId(String clientId);
}
