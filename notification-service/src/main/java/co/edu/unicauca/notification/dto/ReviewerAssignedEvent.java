/*
 * Autores: Britney Benítez, Juan Manuel García,
 *          Yineidy Mamián Jiménez, Martín Silva Cabrera
 * Laboratorio de Ingeniería de Software II - Universidad del Cauca
 */
package co.edu.unicauca.notification.dto;

/** Evento recibido desde Review Service. */
public record ReviewerAssignedEvent(Long questionId, Long reviewerId, Long assignedBy, String occurredAt) {}
