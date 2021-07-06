package com.qtech.it.Controller;

import com.qtech.it.Dao.UserRepository;
import com.qtech.it.Po.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

//    @RequestMapping("/regist")
//    public String regist(){
//        return "/user/index";
//    }

    @GetMapping(path="/doregist") // Map ONLY GET REQUESTs.
//    @ResponseBody 在public 之后已被注销
//    @RestController 是 @Controller 和 @ResponseBody的集合，使用 @RestController 会返回 "index"字符串
//    @ResponseBody注解的作用是将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，然后直接输出到页面，并不会走视图处理器
    public  String addNewUser (@RequestParam String name, @RequestParam String password, User user, Map<String,Object> map) throws DataIntegrityViolationException {

        try {
            if (name == null || password == null || name.equals("") || password.equals("")) {
                log.warn("不能提交null值![在控制台上显示]");
                map.put("msg","用户名和密码均不能为空！");
                return "/user/view_page";
            } else {
                // @ResponseBody means the returned String is a response, not a view name.
                user.setName(name);
                user.setPassword(password);
                userRepository.save(user);
                log.info(user.toString()+" 注册成功！[在控制台上显示]");
                map.put("msg","注册成功！");
                return "/user/view_page";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("用户名重复注册，请换个用户名重新注册~");
            map.put("msg","用户名重复注册，请换个用户名重新注册~");
            return "/user/view_page";
        }
    }

//    @RequestMapping("/login")
//    public String login(){
//        return "/user/index";
//    }

    /**
     * 登陆方法, 用户输入用户名和密码, 查询数据库检验是否有该账户,如果有,
     * 返回原先页面 ,登陆成功。
     * @param model Spring MVC中的Model，用来储存经过controller处理后的信息，再由View层渲染
     *         得到前端页面。
     * @return
     */
    @GetMapping(path = "/dologin")
    public String login(@RequestParam String name, @RequestParam String password, Model model,Map<String,Object> map) throws IndexOutOfBoundsException{

        try {
            List<User> users = userRepository.findByName(name);
            // 如果数据库中未查到该账号:
            if (users == null || name == null || password == null || name.equals("") || password.equals("")) {
    //            log.warn("attempting to log in with the non-existed account");
                map.put("msg","该用户不存在! 或者 用户名和密码均不能为空");
                return "/user/view_page";
            } else {
                User user = users.get(0);
                if (user.getName().equals(name) && user.getPassword().equals(password)) {
                    // 如果密码与用户配对成功:
                    model.addAttribute("name", user.getName());
    //                log.warn(user.toString()+ "登录成功");
                    map.put("msg","登录成功！");
                    return "/user/success";
                } else {
                    // 如果密码与用户不匹配:
                    model.addAttribute("name", "登录失败");
    //                log.warn(user.toString()+ "登录失败");
                    map.put("msg","登录失败！");
                    return "/user/view_page";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("该用户名还没有被注册，不能登录~");
            map.put("msg","该用户名还没有被注册，不能登录~");
            return "/user/view_page";
        }
    }

    /**
     * 查看所有用户的注册信息，按照Spring Boot的设定，以Json的形式输送给用户端。
     * @return
     */
    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 域名的根目录，然后返回的“index”会映射到
     * java/resources/templates/index.html文件
     */
    @GetMapping(path="/")
    public String welcomePage(@RequestParam(name="name", required=false, defaultValue="World")
                                      String namel){
        return "/user/index";
    }


}
