import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class PongController implements Initializable {

    @FXML
    private Pane board;

    @FXML
    private Rectangle computer, player;

    @FXML
    private Circle ball;

    @FXML
    private Label labelScore;

    private double playerVelocityY = 0;

    private AnimationTimer timer;

    public PongController() {
        timer = new AnimationTimer() {
            // la méthode handle représente la boucle de jeu
            @Override
            public void handle(long l) {
                // on récupère les entrées de notre joueur(méthod static)
                PongUtils.handlePlayer(player, playerVelocityY);
                // on met a jour les déplacements des objets(méthod static)
                PongUtils.updateGame(player, computer, ball);
                // on check si la partie est terminée
                chekEndGame();
            }

        };
    }

    @FXML
    public void reset(ActionEvent event) {
        timer.stop();
        // et on remet les éléments à leur état initial
        ball.setCenterX(450);
        ball.setCenterY(300);
        player.setY(200);
        computer.setY(ball.getCenterY());
        PongUtils.resetGame();
    }

    @FXML
    public void run(ActionEvent event) {
        board.requestFocus();
        //on démarre le timer
        timer.start();
        computer.setY(ball.getCenterY());
    }

    @FXML
    public void onKeyPressed(KeyEvent event) {
        
        if (ball.getCenterX() > 15) {
            switch (event.getCode()) {
                case Z:
                    if (player.getY() >= 0) {
                        playerVelocityY = -5;
                        player.setY(player.getY() + playerVelocityY);  
                    }
                    break;
                case S:                
                    if (player.getY() <= 600) {
                        playerVelocityY = 5;
                        player.setY(player.getY() + playerVelocityY); 
                    }
                    break;
                default:
                    break;
            }
        }
    }
    @FXML
    public void onKeyReleased(KeyEvent event) {
        // on passe la velocity a 0 quand on lache les touches
        playerVelocityY = 0;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       labelScore.textProperty().bind(Bindings.convert(PongUtils.scoreProperty()));
    }

    public void chekEndGame() {
        if (ball.getCenterX() < 15) {
            // la partie est terminée
            timer.stop();

        }
    }
}