package eop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 默认访问路径
 * 用于没有任何业务逻辑的页面访问
 */
@Controller
public class DefaultController {

    /**
     * 两层目录结构的默认访问路径
     * @param path2
     * @return
     */
    @RequestMapping(value = "/default/{path2}")
    public ModelAndView defulTwoTier(@PathVariable String path2){
        return new ModelAndView("/default/"+path2);
    }

    /**
     * 三层目录结构的默认访问路径
     * @param path2
     * @return
     */
    @RequestMapping(value = "/default/{path2}/{path3}")
    public ModelAndView defulThreeTier(@PathVariable String path2,@PathVariable String path3){
        return new ModelAndView("/default/"+path2+"/"+path3);
    }
}
