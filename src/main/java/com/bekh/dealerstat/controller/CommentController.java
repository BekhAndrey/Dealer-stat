package com.bekh.dealerstat.controller;

import com.bekh.dealerstat.model.Comment;
import com.bekh.dealerstat.service.CommentService;
import com.bekh.dealerstat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Controller
@RequestMapping("/users/{traderId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public String getTraderComments(@PathVariable("traderId") Long traderId, Model model) {
        model.addAttribute("comments", commentService.findAllByTraderId(userService.findById(traderId).getId()));
        return "comment/CommentsPage";
    }

    @GetMapping("/{id}")
    public String getComment(@PathVariable("traderId") Long traderId,
                             @PathVariable("id") Long commentId, Model model) {
        model.addAttribute("comment", commentService.findByIdAndTraderId(commentId, userService.findById(traderId).getId()));
        return "comment/CommentPage";
    }

    @GetMapping("/add")
    public String addCommentForm(@PathVariable("traderId") Long traderId, Model model) {
        model.addAttribute("comment", new Comment());
        return "comment/NewComment";
    }

    @PostMapping("/add")
    public String addComment(@PathVariable("traderId") Long traderId,
                             @Valid Comment comment, Errors errors,
                             Authentication authentication) {
        if(errors.hasErrors()){
            return "comment/NewComment";
        }
        comment.setAuthor(userService.findUserByEmail(authentication.getName()));
        comment.setTrader(userService.findById(traderId));
        commentService.save(comment);
        return "redirect:/users/{traderId}/comments";
    }

    @GetMapping("/{id}/delete")
    public String confirmDelete(@PathVariable("traderId") Long traderId,
                                @PathVariable("id") Long commentId,
                                Authentication authentication) {
        Comment commentToDelete = commentService.findByIdAndTraderId(commentId, traderId);
        if (!commentToDelete.getAuthor().getId()
                .equals(userService.findUserByEmail(authentication.getName()).getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the author of this comment");
        }
        return "comment/ConfirmDeleteComment";
    }

    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable("traderId") Long traderId,
                                @PathVariable("id") Long commentId) {
        commentService.delete(commentService.findByIdAndTraderId(commentId, traderId));
        return "redirect:/users/{traderId}/comments";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("traderId") Long traderId,
                           @PathVariable("id") Long commentId,
                           Authentication authentication, Model model) {
        Comment commentToEdit = commentService.findByIdAndTraderId(commentId, traderId);
        if (!commentToEdit.getAuthor().getId()
                .equals(userService.findUserByEmail(authentication.getName()).getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the author of this comment");
        }
            model.addAttribute("comment", commentToEdit);
            return "comment/EditComment";
    }

    @PutMapping("/{id}/edit")
    public String updateComment(@PathVariable("traderId") Long traderId,
                                @PathVariable("id") Long commentId,
                                @Valid Comment comment, Errors errors) {
        if(errors.hasErrors()){
            return "comment/EditComment";
        }
        Comment commentToUpdate = commentService.findByIdAndTraderId(commentId, traderId);
        commentToUpdate.setMessage(comment.getMessage());
        commentService.save(commentToUpdate);
        return "redirect:/users/{traderId}/comments/{id}";
    }
}
