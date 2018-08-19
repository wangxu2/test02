package com.pinyougou.shop.security.service;

import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 自定义认证类
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private SellerService sellerService;

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        //1.从数据库根据用户名查询用户的数据
        TbSeller seller = sellerService.findOne(username);
        if (seller == null){
            throw  new UsernameNotFoundException("未知用户名");
        }

        //2.判断用户是否已经审核通过
        if (!"1".equals(seller.getStatus())){
            throw new UsernameNotFoundException("该用户审核未通过");
        }

        //3.获取用户的信息（密码）security已经封装好了，能够自动获取并判断
        //4.匹配验证（由spring security来自动完成）
        return new User(username,seller.getPassword(),AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_SELLER"));
    }
}
