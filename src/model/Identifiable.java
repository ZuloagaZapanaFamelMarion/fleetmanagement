package model;

/**
 * Contrato genérico para entidades que tienen un identificador numérico.
 * Se usa como cota superior (bounded type) en interfaces genéricas.
 */
public interface Identifiable {

    /**
     * Devuelve el identificador único de la entidad.
     * @return identificador único
     */
    Long getId();
}

