package presentation;

import domain.Question;
import domain.QuestionService;
import infra.Observer;
import infra.Subject;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUIObserver1 extends JFrame implements Observer {

    private QuestionService questionService;

    private JLabel lblBorrador;
    private JLabel lblPendiente;
    private JLabel lblEliminada;
    private JLabel lblTotal;

    public GUIObserver1(
            QuestionService questionService
    ) {

        this.questionService = questionService;

        configurarVentana();
        crearComponentes();

        actualizarEstadisticas();

        setVisible(true);
    }

    private void configurarVentana() {

        setTitle("Estadísticas");

        setSize(400, 300);

        setLocation(720, 100);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );
    }

    private void crearComponentes() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                5,
                                1,
                                10,
                                10
                        )
                );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        30,
                        20,
                        30
                )
        );


        JLabel titulo =
                new JLabel(
                        "ESTADÍSTICAS",
                        SwingConstants.CENTER
                );

        titulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        panel.add(titulo);


        lblBorrador = new JLabel();

        lblPendiente = new JLabel();

        lblEliminada = new JLabel();

        lblTotal = new JLabel();


        panel.add(lblBorrador);
        panel.add(lblPendiente);
        panel.add(lblEliminada);
        panel.add(lblTotal);


        add(panel);
    }

    private void actualizarEstadisticas() {

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

        int total =
                preguntas.size();

        lblBorrador.setText(
                "Borrador: " + borrador
        );

        lblPendiente.setText(
                "Pendiente de revisión: " + pendiente
        );

        lblEliminada.setText(
                "Eliminadas: " + eliminada
        );

        lblTotal.setText(
                "Total de preguntas: " + total
        );
    }

   @Override
public void update(Subject subject) {
    actualizarEstadisticas();
}
 
    
}