package service;

import java.util.List;
import model.Identifiable;

/**
 * Contrato genérico para repositorios de entidades con identificador.
 * 
 * El parámetro de tipo T está acotado (bounded type) a tipos que
 * implementen Identifiable, garantizando type safety.
 */
public interface Repository<T extends Identifiable> {

    /**
     * Almacena o actualiza una entidad.
     * @param entity entidad a persistir en memoria
     */
    void save(T entity);

    /**
     * Busca una entidad por su identificador.
     * @param id identificador único
     * @return entidad encontrada o null si no existe
     */
    T findById(Long id);

    /**
     * Devuelve todas las entidades gestionadas por el repositorio.
     * @return lista de entidades
     */
    List<T> findAll();
}

