package com.mvc.jpaboard.controller;

import com.mvc.jpaboard.dao.WebBoardDao;
import com.mvc.jpaboard.domain.WebBoard;
import com.mvc.jpaboard.persistence.CustomCrudRepository;
import com.mvc.jpaboard.persistence.WebBoardRepository;
import com.mvc.jpaboard.vo.PageMaker;
import com.mvc.jpaboard.vo.PageVO;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/boards")
@Slf4j
@RequiredArgsConstructor
public class WebBoardController {

    private final WebBoardRepository webBoardRepository;
    private final CustomCrudRepository customCrudRepository;
    private final WebBoardDao webBoardDao;

    @GetMapping("/list")
    public void list(@ModelAttribute PageVO pvo, Model model){
//            @PageableDefault(
//                    direction = Sort.Direction.DESC,
//                    sort="bno",
//                    size=10,
//                    page=0
//            ) Pageable page
//
        Pageable page = pvo.makePageable(0, "bno");
//        Predicate predicate = webBoardRepository.makePredicate(pvo.getType(), pvo.getKeyword());
//        Page<WebBoard> result = webBoardRepository.findAll(predicate, page);
//        Page<Object[]> result = customCrudRepository.getCustomPage(pvo.getType(), pvo.getKeyword(), page);
        Page<Object[]> result = webBoardDao.getCustomPage(pvo.getType(), pvo.getKeyword(), page);
        log.info("---------------------------------------------------");
        log.info("list() called");
        Optional.of(pvo).ifPresent(vo -> log.info("pageVO : {}", pvo.toString()));
        log.info("Page<WebBoard> : {}", result.toString());
        log.info("---------------------------------------------------");

        model.addAttribute("result", new PageMaker<>(result));
    }

    @GetMapping("/register")
    public void registerGet(@ModelAttribute("vo") WebBoard vo) {
        log.info(" register get ");
    }

    @Secured(value={"ROLE_BASIC", "ROLE_MANAGER", "ROLE_ADMIN"})
    @PostMapping("/register")
    public String registerPost(@ModelAttribute("vo") WebBoard vo, RedirectAttributes rttr){
        log.info("---------------------------------------------------");
        log.info("register called()..");
        log.info("---------------------------------------------------");
        webBoardRepository.save(vo);
        rttr.addFlashAttribute("msg", "success");
        return "redirect:/boards/list";
    }

    @GetMapping("/view")
    public void view(Long bno, @ModelAttribute("pageVO") PageVO pageVO, Model model) {
        log.info("---------------------------------------------------");
        log.info("view bno : {}", bno);
        log.info("---------------------------------------------------");
        webBoardRepository.findById(bno).ifPresent(board->model.addAttribute("board", board));
    }

    @GetMapping("/modify")
    public void modify(Long bno, @ModelAttribute("pageVO") PageVO pageVO, Model model){
        log.info("---------------------------------------------------");
        log.info("modify bno : {}", bno);
        log.info("---------------------------------------------------");
        webBoardRepository.findById(bno).ifPresent(board->model.addAttribute("board", board));
    }

    @Secured(value={"ROLE_BASIC", "ROLE_MANAGER", "ROLE_ADMIN"})
    @PostMapping("/delete")
    public String delete(Long bno, PageVO pageVo, RedirectAttributes rttr) {
        log.info("---------------------------------------------------");
        log.info("delete bno : {}", bno);
        Optional.of(pageVo).ifPresent(vo -> log.info("pageVO : {}", vo.toString()));
        log.info("---------------------------------------------------");
        webBoardRepository.deleteById(bno);
        // URL에 숨겨져서 전송
        rttr.addFlashAttribute("msg", "success");
        // URL에 추가되어 전송
        rttr.addAttribute("page", pageVo.getPage());
        rttr.addAttribute("size", pageVo.getSize());
        rttr.addAttribute("type", pageVo.getType());
        rttr.addAttribute("keyword", pageVo.getKeyword());

        return "redirect:/boards/list";
    }

    @Secured(value={"ROLE_BASIC", "ROLE_MANAGER", "ROLE_ADMIN"})
    @PostMapping("/modify")
    public String modifyPost(WebBoard board, PageVO pageVO, RedirectAttributes rttr) {
        log.info("---------------------------------------------------");
        log.info("modifyPost bno : {}", board.getBno());
        Optional.of(pageVO).ifPresent(vo -> log.info("pageVO : {}", vo.toString()));
        log.info("---------------------------------------------------");

        webBoardRepository.findById(board.getBno()).ifPresent(result -> {
            result.setTitle(board.getTitle());
            result.setWriter(board.getWriter());
            result.setContent(board.getContent());

            webBoardRepository.save(result);
            rttr.addFlashAttribute("msg", "success");
            rttr.addAttribute("bno", result.getBno());
        });
        rttr.addAttribute("page", pageVO.getPage());
        rttr.addAttribute("size", pageVO.getSize());
        rttr.addAttribute("type", pageVO.getType());
        rttr.addAttribute("keyword", pageVO.getKeyword());

        return "redirect:/boards/view";
    }
}
