package presentation;

import domain.Question;
import domain.QuestionService;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class GUIQuestions extends JFrame {

    private QuestionService questionService;

    private JComboBox<Question> comboPreguntas;

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextArea txtPregunta;

    private JTextField txtOpcionA;
    private JTextField txtOpcionB;
    private JTextField txtOpcionC;
    private JTextField txtOpcionD;

    private JTextField txtRespuestaCorrecta;

    private JComboBox<String> comboEstado;

    private JButton btnActualizar;

    public GUIQuestions(QuestionService questionService) {

        this.questionService = questionService;

        configurarVentana();
        crearComponentes();
        cargarPreguntas();

        setVisible(true);
    }

    // =========================================================
    // CONFIGURAR VENTANA
    // =========================================================

    private void configurarVentana() {

        setTitle("Banco de Preguntas - Saber Pro");

        setSize(650, 650);

        setLocation(50, 50);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));
    }

    // =========================================================
    // CREAR COMPONENTES
    // =========================================================

    private void crearComponentes() {

        // -----------------------------------------------------
        // PARTE SUPERIOR
        // -----------------------------------------------------

        JPanel panelSuperior = new JPanel(
                new FlowLayout(FlowLayout.LEFT)
        );

        JLabel lblPregunta = new JLabel(
                "Seleccionar pregunta:"
        );

        comboPreguntas = new JComboBox<>();

        comboPreguntas.setPreferredSize(
                new Dimension(400, 30)
        );

        comboPreguntas.addActionListener(
                e -> cargarPreguntaSeleccionada()
        );

        panelSuperior.add(lblPregunta);
        panelSuperior.add(comboPreguntas);

        add(
                panelSuperior,
                BorderLayout.NORTH
        );


        // -----------------------------------------------------
        // FORMULARIO
        // -----------------------------------------------------

        JPanel panelFormulario = new JPanel();

        panelFormulario.setLayout(
                new BoxLayout(
                        panelFormulario,
                        BoxLayout.Y_AXIS
                )
        );

        panelFormulario.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        20,
                        10,
                        20
                )
        );


        // ID

        JPanel panelId = new JPanel(
                new BorderLayout(10, 5)
        );

        panelId.add(
                new JLabel("ID:"),
                BorderLayout.WEST
        );

        txtId = new JTextField();

        txtId.setEditable(false);

        panelId.add(
                txtId,
                BorderLayout.CENTER
        );

        panelFormulario.add(panelId);

        panelFormulario.add(
                Box.createVerticalStrut(10)
        );


        // NOMBRE

        JPanel panelNombre = new JPanel(
                new BorderLayout(10, 5)
        );

        panelNombre.add(
                new JLabel("Nombre:"),
                BorderLayout.WEST
        );

        txtNombre = new JTextField();

        txtNombre.setEditable(false);

        panelNombre.add(
                txtNombre,
                BorderLayout.CENTER
        );

        panelFormulario.add(panelNombre);

        panelFormulario.add(
                Box.createVerticalStrut(10)
        );


        // PREGUNTA

        panelFormulario.add(
                new JLabel("Pregunta:")
        );

        txtPregunta = new JTextArea(4, 40);

        txtPregunta.setLineWrap(true);
        txtPregunta.setWrapStyleWord(true);
        txtPregunta.setEditable(false);

        JScrollPane scrollPregunta =
                new JScrollPane(txtPregunta);

        panelFormulario.add(scrollPregunta);

        panelFormulario.add(
                Box.createVerticalStrut(10)
        );


        // OPCIÓN A

        txtOpcionA = crearCampoOpcion(
                panelFormulario,
                "A:"
        );


        // OPCIÓN B

        txtOpcionB = crearCampoOpcion(
                panelFormulario,
                "B:"
        );


        // OPCIÓN C

        txtOpcionC = crearCampoOpcion(
                panelFormulario,
                "C:"
        );


        // OPCIÓN D

        txtOpcionD = crearCampoOpcion(
                panelFormulario,
                "D:"
        );


        // RESPUESTA CORRECTA

        JPanel panelRespuesta =
                new JPanel(
                        new BorderLayout(10, 5)
                );

        panelRespuesta.add(
                new JLabel("Respuesta correcta:"),
                BorderLayout.WEST
        );

        txtRespuestaCorrecta =
                new JTextField();

        txtRespuestaCorrecta.setEditable(false);

        panelRespuesta.add(
                txtRespuestaCorrecta,
                BorderLayout.CENTER
        );

        panelFormulario.add(panelRespuesta);

        panelFormulario.add(
                Box.createVerticalStrut(10)
        );


        // ESTADO

        JPanel panelEstado =
                new JPanel(
                        new BorderLayout(10, 5)
                );

        panelEstado.add(
                new JLabel("Estado:"),
                BorderLayout.WEST
        );

        comboEstado = new JComboBox<>(
                new String[]{
                        "Borrador",
                        "Pendiente de revisión",
                        "Eliminada"
                }
        );

        panelEstado.add(
                comboEstado,
                BorderLayout.CENTER
        );

        panelFormulario.add(panelEstado);


        add(
                new JScrollPane(panelFormulario),
                BorderLayout.CENTER
        );


        // -----------------------------------------------------
        // BOTÓN
        // -----------------------------------------------------

        JPanel panelBotones = new JPanel();

        btnActualizar = new JButton(
                "Actualizar estado"
        );

        btnActualizar.addActionListener(
                e -> actualizarEstado()
        );

        panelBotones.add(btnActualizar);

        add(
                panelBotones,
                BorderLayout.SOUTH
        );
    }

    // =========================================================
    // CREAR CAMPO DE OPCIÓN
    // =========================================================

    private JTextField crearCampoOpcion(
            JPanel panel,
            String etiqueta
    ) {

        JPanel panelOpcion =
                new JPanel(
                        new BorderLayout(10, 5)
                );

        JLabel label =
                new JLabel(etiqueta);

        JTextField campo =
                new JTextField();

        campo.setEditable(false);

        panelOpcion.add(
                label,
                BorderLayout.WEST
        );

        panelOpcion.add(
                campo,
                BorderLayout.CENTER
        );

        panel.add(panelOpcion);

        panel.add(
                Box.createVerticalStrut(5)
        );

        return campo;
    }

    // =========================================================
    // CARGAR PREGUNTAS
    // =========================================================

    private void cargarPreguntas() {

        List<Question> preguntas =
                questionService.getAllQuestions();

        comboPreguntas.removeAllItems();

        for (Question question : preguntas) {

            comboPreguntas.addItem(question);
        }

        if (!preguntas.isEmpty()) {

            comboPreguntas.setSelectedIndex(0);

            cargarPreguntaSeleccionada();
        }
    }

    // =========================================================
    // CARGAR PREGUNTA SELECCIONADA
    // =========================================================

    private void cargarPreguntaSeleccionada() {

        Question question =
                (Question) comboPreguntas.getSelectedItem();

        if (question == null) {
            return;
        }

        // ID
        txtId.setText(
                question.getId()
        );

        // NOMBRE
        txtNombre.setText(
                question.getName()
        );

        // PREGUNTA
        txtPregunta.setText(
                question.getContext()
        );

        // OPCIONES

        List<String> opciones =
                question.getOptions();

        if (opciones.size() > 0) {
            txtOpcionA.setText(opciones.get(0));
        }

        if (opciones.size() > 1) {
            txtOpcionB.setText(opciones.get(1));
        }

        if (opciones.size() > 2) {
            txtOpcionC.setText(opciones.get(2));
        }

        if (opciones.size() > 3) {
            txtOpcionD.setText(opciones.get(3));
        }

        // RESPUESTA

        txtRespuestaCorrecta.setText(
                question.getCorrectAnswer()
        );

        // ESTADO

        comboEstado.setSelectedItem(
                question.getState()
        );
    }

    // =========================================================
    // ACTUALIZAR ESTADO
    // =========================================================

    private void actualizarEstado() {

        Question question =
                (Question) comboPreguntas.getSelectedItem();

        if (question == null) {
            return;
        }

        String nuevoEstado =
                (String) comboEstado.getSelectedItem();

        String estadoAnterior =
                question.getState();

        if (estadoAnterior.equals(nuevoEstado)) {

            JOptionPane.showMessageDialog(
                    this,
                    "El estado no ha cambiado.",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        questionService.updateQuestionState(
                question.getId(),
                nuevoEstado
        );

        JOptionPane.showMessageDialog(
                this,
                "Estado actualizado correctamente.",
                "Banco de preguntas",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}