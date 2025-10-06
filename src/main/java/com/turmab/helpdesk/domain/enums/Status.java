package com.turmab.helpdesk.domain.enums;

/**
 * Enumeração que define os possíveis status de um chamado.
 */
public enum Status {

    ABERTO(0, "ABERTO"),
    ANDAMENTO(1, "ANDAMENTO"),
    ENCERRADO(2, "ENCERRADO");

    private Integer codigo;
    private String descricao;

    private Status(Integer codigo, String descricao) {
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
     * Converte um código numérico para uma instância do enum Status.
     * * @param cod O código a ser convertido.
     * @return O Status correspondente.
     * @throws IllegalArgumentException se o código não for válido.
     */
    public static Status toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }
        for (Status x : Status.values()) {
            if (cod.equals(x.getCodigo())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Status Inválido: " + cod);
    }
}