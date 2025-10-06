package com.turmab.helpdesk.domain.enums;

/**
 * Enumeração que define os níveis de prioridade de um chamado.
 */
public enum Prioridade {

    BAIXA(0, "BAIXA"),
    MEDIA(1, "MEDIA"),
    ALTA(2, "ALTA");

    private Integer codigo;
    private String descricao;

    private Prioridade(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * Converte um código numérico para uma instância do enum Prioridade.
     * * @param cod O código a ser convertido.
     * @return A Prioridade correspondente.
     * @throws IllegalArgumentException se o código não for válido.
     */
    public static Prioridade toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }
        for (Prioridade x : Prioridade.values()) {
            if (cod.equals(x.getCodigo())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Prioridade Inválida: " + cod);
    }
}