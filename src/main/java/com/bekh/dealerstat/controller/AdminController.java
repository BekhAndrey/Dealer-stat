package com.bekh.dealerstat.controller;

import com.bekh.dealerstat.model.Comment;
import com.bekh.dealerstat.model.GameObject;
import com.bekh.dealerstat.service.CommentService;
import com.bekh.dealerstat.service.GameObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private GameObjectService gameObjectService;

    @GetMapping("/comments")
    public String notApprovedComments(Model model) {
        model.addAttribute("comments", commentService.findAllByApproved(false));
        return "credentials/NotApprovedComments";
    }

    @GetMapping("/comments/{id}/approve")
    public String confirmApproveComment(@PathVariable("id") Long commentId) {
        return "credentials/ConfirmApprove";
    }

    @PutMapping("/comments/{id}/approve")
    public String approveComment(@PathVariable("id") Long commentId) {
        Comment commentToApprove = commentService.findById(commentId);
        commentToApprove.setApproved(true);
        commentService.save(commentToApprove);
        return "redirect:/admin/comments";
    }

    @GetMapping("/objects")
    public String notApprovedObjects(Model model) {
        model.addAttribute("objects", gameObjectService.findAllByApproved(false));
        return "game-object/NotApprovedObjects";
    }

    @GetMapping("/objects/{id}/approve")
    public String confirmApproveObject(@PathVariable("id") Long commentId) {
        return "game-object/ConfirmApproveObject";
    }

    @PutMapping("/objects/{id}/approve")
    public String approveObject(@PathVariable("id") Long objectId) {
        GameObject gameObjectToApprove = gameObjectService.findById(objectId);
        gameObjectToApprove.setApproved(true);
        gameObjectService.save(gameObjectToApprove);
        return "redirect:/admin/objects";
    }
}
