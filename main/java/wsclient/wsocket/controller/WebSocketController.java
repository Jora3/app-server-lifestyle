package wsclient.wsocket.controller;

import com.google.gson.Gson;
import com.lifestile.system.accessdb.client.gestion.PublicationDAO;
import com.lifestile.system.client.gestion.Commentaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "*")
public class WebSocketController {
    private final SimpMessagingTemplate template;
    private final Gson gson = new Gson();

    @Autowired
    public WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/lifestyles/reactions")
    public void onReceivedReaction(String message) {
        this.template.convertAndSend("/publication/reaction", message);
    }

    @MessageMapping("/lifestyles/commentaires")
    public void onReceivedCommentaire(String commentaire) {
        Commentaire coms = gson.fromJson(commentaire, Commentaire.class);
        try {
            this.template.convertAndSend("/publication/commentaire/"+coms.getPublicationId(), commentaire);
            new PublicationDAO().doCommentaire(coms.getPersonneId(), coms.getPublicationId(), coms.getCommentaire());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/start")
    public String start() {
        return "start";
    }
}