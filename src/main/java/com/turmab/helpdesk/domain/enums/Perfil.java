package com.turmab.helpdesk.domain.enums;

/**
 * Enumeração que define os perfis de usuário no sistema.
 * Garante que os perfis tenham um código fixo e uma descrição padrão (Role)
 * para integração com o Spring Security.
 */
public enum Perfil {
	
	ADMIN(0, "ROLE_ADMIN"),
	CLIENTE(1, "ROLE_CLIENTE"),
	TECNICO(2, "ROLE_TECNICO");
	
	private Integer codigo;
	private String descricao;
	
	private Perfil(Integer codigo, String descricao) {
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
	 * Converte um código numérico para uma instância do enum Perfil.
	 * * @param codigo O código a ser convertido.
	 * @return O Perfil correspondente.
	 * @throws IllegalArgumentException se o código não for válido.
	 */
	public static Perfil toEnum(Integer codigo) {
		if(codigo == null) {
			return null;
		}
		
		for(Perfil x : Perfil.values()) {
			if(codigo.equals(x.getCodigo())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Perfil Inválido: " + codigo);
	}
}