package com.zyy;

import com.zyy.mall.product.entity.BrandEntity;
import com.zyy.mall.product.service.BrandService;
import com.zyy.mall.product.service.CategoryService;
import com.zyy.mall.product.util.QiNiuUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MallProductsApplicationTests {

    @Autowired
    private BrandService brandService;

    @Autowired
    private QiNiuUtil qiNiuUtil;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void contextLoads() {

        BrandEntity brand = new BrandEntity();
        brand.setName("OPPO");
        brandService.save(brand);
        System.out.println("ok");
    }

    @Test
    public void testUpload() {
        String str = qiNiuUtil.upload("C:\\Users\\Zooey\\Desktop\\Spring Security\\Snipaste_2022-08-21_21-54-06.png");
        System.out.println("test upload ->>>>" + str);
    }

    @Test
    public void testUploadStream() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Zooey\\Desktop\\tb.jpg");
        qiNiuUtil.upload(fileInputStream, "tb");
    }

    @Test
    public void testFindCatelogPaths() {
        Long[] catelogPath = categoryService.findCatelogPath(883L);
        log.info("完整路径为: {}", Arrays.asList(catelogPath));
    }

}
