package presentation;

import domain.Question;
import domain.QuestionService;
import infra.Observer;
import infra.Subject;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class GUIObserver2 extends JFrame implements Observer {

    private QuestionService questionService;

    private PanelGrafica panelGrafica;

    public GUIObserver2(
            QuestionService questionService
    ) {

        this.questionService = questionService;

        configurarVentana();

        panelGrafica = new PanelGrafica();

        add(panelGrafica);

        actualizarGrafica();

        setVisible(true);
    }

    private void configurarVentana() {

        setTitle("Gráfica de preguntas");

        setSize(500, 500);

        setLocation(720, 420);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );
    }

    private void actualizarGrafica() {

        List<Question> preguntas =
                questionService.getAllQuestions();

        int borrador = 0;
        int pendiente = 0;
        int eliminada = 0;

        for (Question question : preguntas) {

            switch (question.getState()) {

                case "Borrador":
                    borrador++;
                    break;

                case "Pendiente de revisión":
                    pendiente++;
                    break;

                case "Eliminada":
                    eliminada++;
                    break;
            }
        }

        panelGrafica.setDatos(
                borrador,
                pendiente,
                eliminada
        );

        panelGrafica.repaint();
    }

   @Override
public void update(Subject subject) {
    actualizarGrafica();
}
 

    // =========================================================
    // PANEL DE LA GRÁFICA
    // =========================================================

    private static class PanelGrafica
            extends JPanel {

        private int borrador;
        private int pendiente;
        private int eliminada;

        public void setDatos(
                int borrador,
                int pendiente,
                int eliminada
        ) {

            this.borrador = borrador;
            this.pendiente = pendiente;
            this.eliminada = eliminada;
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(g);

            int total =
                    borrador
                            + pendiente
                            + eliminada;

            if (total == 0) {

                g.drawString(
                        "No hay preguntas",
                        180,
                        200
                );

                return;
            }


            Graphics2D g2 =
                    (Graphics2D) g;

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );


            // -------------------------------------------------
            // TÍTULO
            // -------------------------------------------------

            g2.setColor(Color.BLACK);

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            20
                    )
            );

            g2.drawString(
                    "Preguntas por estado",
                    140,
                    40
            );


            // -------------------------------------------------
            // ÁNGULOS
            // -------------------------------------------------

            int anguloBorrador =
                    (int) (
                            360.0
                                    * borrador
                                    / total
                    );

            int anguloPendiente =
                    (int) (
                            360.0
                                    * pendiente
                                    / total
                    );

            int anguloEliminada =
                    360
                            - anguloBorrador
                            - anguloPendiente;


            int x = 100;
            int y = 70;

            int ancho = 280;
            int alto = 280;


            // BORRADOR

            g2.setColor(Color.BLUE);

            g2.fillArc(
                    x,
                    y,
                    ancho,
                    alto,
                    0,
                    anguloBorrador
            );


            // PENDIENTE

            g2.setColor(Color.ORANGE);

            g2.fillArc(
                    x,
                    y,
                    ancho,
                    alto,
                    anguloBorrador,
                    anguloPendiente
            );


            // ELIMINADA

            g2.setColor(Color.RED);

            g2.fillArc(
                    x,
                    y,
                    ancho,
                    alto,
                    anguloBorrador
                            + anguloPendiente,
                    anguloEliminada
            );


            // -------------------------------------------------
            // LEYENDA
            // -------------------------------------------------

            g2.setColor(Color.BLUE);

            g2.fillRect(
                    80,
                    380,
                    15,
                    15
            );

            g2.setColor(Color.BLACK);

            g2.drawString(
                    "Borrador: " + borrador,
                    105,
                    393
            );


            g2.setColor(Color.ORANGE);

            g2.fillRect(
                    230,
                    380,
                    15,
                    15
            );

            g2.setColor(Color.BLACK);

            g2.drawString(
                    "Pendiente: " + pendiente,
                    255,
                    393
            );


            g2.setColor(Color.RED);

            g2.fillRect(
                    80,
                    410,
                    15,
                    15
            );

            g2.setColor(Color.BLACK);

            g2.drawString(
                    "Eliminada: " + eliminada,
                    105,
                    423
            );
        }
    }
}