package com.comicsqueeze.comicsqueeze.service;


import com.comicsqueeze.comicsqueeze.object.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comicsqueeze.comicsqueeze.object.Page;
import com.comicsqueeze.comicsqueeze.repository.PageRepo;
import sun.jvm.hotspot.gc.cms.ParNewGeneration;

@Service
public class ComicPageService {

    @Autowired
    private PageRepo pageRepo;
    public Page findPageByNumber(String username, int pagenumber, String seriesTitle, String issueTitle){
        Page existing = pageRepo.findByPageNumber(username,pagenumber,seriesTitle,issueTitle);
        if (existing == null)
        {
            System.out.println("null existing");
            return null;
        }
        else
        {
            System.out.println("page exists");
            System.out.println(existing.getIssue());
            System.out.println(existing.getPagenumber());
            return existing;
        }
    }
    public void createPage(Page newPage){
        pageRepo.createPage(newPage);
    }

    public void deletePage(Page thePage) { pageRepo.deletePage(thePage); }

}
