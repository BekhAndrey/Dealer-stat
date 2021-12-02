package com.bekh.dealerstat.controller;


import com.bekh.dealerstat.model.Comment;
import com.bekh.dealerstat.model.User;
import com.bekh.dealerstat.service.CommentService;
import com.bekh.dealerstat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public String addCommentForm(@PathVariable("traderId") Long traderId, @SessionAttribute("loggedUser") User user, Model model) {
        if (user.getId() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorised");
        }
        model.addAttribute("comment", new Comment());
        return "comment/NewComment";
    }

    @PostMapping
    public String addComment(@SessionAttribute("loggedUser") User user, @PathVariable("traderId") Long traderId, Comment comment, Model model) {
        comment.setAuthor(user);
        comment.setTrader(userService.findById(traderId));
        model.addAttribute("comment", comment);
        commentService.save(comment);
        return "redirect:/users/{traderId}/comments";
    }

    @GetMapping("/{id}/delete")
    public String confirmDelete(@PathVariable("traderId") Long traderId,
                                @PathVariable("id") Long commentId,
                                @SessionAttribute("loggedUser") User user, Model model) {
        Comment commentToDelete = commentService.findByIdAndTraderId(commentId, traderId);
        if (user.getId() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorised");
        } else if (!commentToDelete.getAuthor().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the author of this comment");
        } else {
            return "comment/ConfirmDeleteComment";
        }
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
                           @SessionAttribute("loggedUser") User user, Model model) {
        Comment commentToEdit = commentService.findByIdAndTraderId(commentId, traderId);
        if (user.getId() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorised");
        } else if (!commentToEdit.getAuthor().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the author of this comment");
        } else {
            model.addAttribute("comment", commentToEdit);
            return "comment/EditComment";
        }
    }

    @PutMapping("/{id}")
    public String updateComment(@PathVariable("traderId") Long traderId,
                                @PathVariable("id") Long commentId, Comment comment) {
        Comment commentToUpdate = commentService.findByIdAndTraderId(commentId, traderId);
        commentToUpdate.setMessage(comment.getMessage());
        commentService.save(commentToUpdate);
        return "redirect:/users/{traderId}/comments/{id}";
    }

}
