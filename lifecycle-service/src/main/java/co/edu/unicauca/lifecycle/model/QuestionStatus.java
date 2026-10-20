/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.lifecycle.model;

/** Estados de una pregunta y sus transiciones permitidas. */
public enum QuestionStatus {
    DRAFT, PENDING_REVIEW, IN_REVIEW, APPROVED, REJECTED, PUBLISHED, ARCHIVED;

    public boolean canTransitionTo(QuestionStatus n) {
        return switch (this) {
            case DRAFT -> n == PENDING_REVIEW;
            case PENDING_REVIEW -> n == IN_REVIEW;
            case IN_REVIEW -> n == APPROVED || n == REJECTED;
            case REJECTED -> n == DRAFT || n == PENDING_REVIEW; // corrección y reenvío
            case APPROVED -> n == PUBLISHED;
            case PUBLISHED -> n == ARCHIVED;
            case ARCHIVED -> false;
        };
    }
}
