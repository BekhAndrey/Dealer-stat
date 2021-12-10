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

    private final CommentService commentService;

    private final GameObjectService gameObjectService;

    public AdminController(CommentService commentService, GameObjectService gameObjectService) {
        this.commentService = commentService;
        this.gameObjectService = gameObjectService;
    }

    @GetMapping("/comments")
    public String notApprovedComments(Model model) {
        model.addAttribute("comments", commentService.findAllByApproved(false));
        return "comment/NotApprovedComments";
    }

    @GetMapping("/comments/{id}/approve")
    public String confirmApproveComment(@PathVariable("id") Long commentId) {
        return "comment/ConfirmApprove";
    }

    @PutMapping("/comments/{id}/approve")
    public String approveComment(@PathVariable("id") Long commentId) {
        Comment commentToApprove = commentService.findById(commentId);
        commentToApprove.setApproved(true);
        commentService.save(commentToApprove);
        return "redirect:/admin/comments";
    }

    @GetMapping("/comments/{id}/decline")
    public String confirmDeclineComment(@PathVariable("id") Long commentId) {
        return "comment/ConfirmDecline";
    }

    @PutMapping("/comments/{id}/decline")
    public String declineComment(@PathVariable("id") Long commentId) {
        Comment commentToDecline = commentService.findById(commentId);
        commentService.delete(commentToDecline);
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

    @GetMapping("/objects/{id}/decline")
    public String confirmDeclineObject(@PathVariable("id") Long commentId) {
        return "game-object/ConfirmDeclineObject";
    }

    @PutMapping("/objects/{id}/decline")
    public String declineObject(@PathVariable("id") Long objectId) {
        GameObject gameObjectToDecline = gameObjectService.findById(objectId);
        gameObjectService.delete(gameObjectToDecline);
        return "redirect:/admin/objects";
    }
}
