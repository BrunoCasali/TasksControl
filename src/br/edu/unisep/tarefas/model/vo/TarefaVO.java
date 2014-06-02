package br.edu.unisep.tarefas.model.vo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class TarefaVO {

	private int id;
	private int status;
	private SimpleStringProperty descricao;
	private SimpleObjectProperty<Date> data;

	public TarefaVO(){
		data = new SimpleObjectProperty<Date>(new Date());
		descricao = new SimpleStringProperty();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDescricao() {
		return descricao.getValue();
	}

	public void setDescricao(String descricao) {
		this.descricao.setValue(descricao);
	}

	public SimpleStringProperty descricao() {
		return descricao;
	}

	public SimpleObjectProperty<Date> data(){
		return data;
	}
	
	public Date getData() {
		return data.getValue();
	}

	public void setData(Date data) {
		this.data.setValue(data);
	}

	public String getDescricaoStatus() {

		if (getStatus() == 0) {
			return "Em Aberto";
		} else if (getStatus() == 1) {
			return "Em Andamento";
		} else if (getStatus() == 2) {
			return "Paralizada";
		} else if (getStatus() == 3) {
			return "Cancelada";
		} else if (getStatus() == 4) {
			return "Finalizada";
		} else {
			return "Cancelada";
		}
	}

	public String getDsData() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String dsData = df.format(data);
		return dsData;
	}
}