package br.edu.unisep.tarefas;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import br.edu.unisep.exception.DAOException;
import br.edu.unisep.tarefas.model.dao.TarefaDAO;
import br.edu.unisep.tarefas.model.vo.TarefaVO;

public class TarefasController implements Initializable {

	@FXML private TextField txtDescricao;
	@FXML private TextField txtData;
	@FXML private TableView tabTarefas;
	@FXML private Button btnIniciar;
	@FXML private Button btnCancelar;
	@FXML private Button btnParalizar;
	@FXML private Button btnFinalizar;
	
	private TarefaVO tarefa;
	
	private ObservableList<TarefaVO> ListaTarefas;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		ObservableList<TableColumn> colunas = tabTarefas.getColumns();
 		colunas.get(0).setCellValueFactory(new PropertyValueFactory("descricao"));
		colunas.get(1).setCellValueFactory(new PropertyValueFactory("data"));
		colunas.get(2).setCellValueFactory(new PropertyValueFactory("descricaoStatus"));

		ListaTarefas = FXCollections.observableArrayList();
		tabTarefas.setItems(ListaTarefas);
		bindCampos();
		listar();
	}
	
	private void bindCampos(){
		this.tarefa = new TarefaVO();
		txtDescricao.textProperty().bindBidirectional(tarefa.descricao());
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		txtData.textProperty().bindBidirectional(tarefa.data(), df);
		tarefa.setData(null);
	}

	private void listar() {

		try {

			TarefaDAO dao = new TarefaDAO();

			List<TarefaVO> lista = dao.listar();
			ListaTarefas.clear();
			ListaTarefas.addAll(lista);

		} catch (DAOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void salvar(ActionEvent event) {

		TarefaDAO dao = new TarefaDAO();

		try {
			dao.salvar(tarefa);

			txtDescricao.setText("");
			txtData.setText("");

			listar();

			txtDescricao.requestFocus();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void iniciarTarefa(ActionEvent e) {

		// obtem o objeto da tabela que foi selecionado pelo usuarios se nenhum
		// objeto esta selecionado, o metodo ira retornar null.
		TarefaVO tarefa = (TarefaVO) tabTarefas.getSelectionModel().getSelectedItem();

		// verifica se uma tarefa foi selecionada
		if (tarefa != null) {

			// verifica se o status � - em aberto ou 2 - paralizada
			if (tarefa.getStatus() == 0 || tarefa.getStatus() == 2) {
				tarefa.setStatus(1);

				try {
					// Atualiza o status da tarefa no banco de dados.
					TarefaDAO dao = new TarefaDAO();
					dao.atualizar(tarefa);

					// lista novamente as tarefas atualizadas
					listar();
				} catch (DAOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@FXML
	public void paralizarTarefa(ActionEvent e) {

		// obtem o objeto da tabela que foi selecionado pelo usuarios se nenhum
		// objeto esta selecionado, o metodo ira retornar null.
		TarefaVO tarefa = (TarefaVO) tabTarefas.getSelectionModel().getSelectedItem();

		// verifica se uma tarefa foi selecionada
		if (tarefa != null) {

			// verifica se o status � - em aberto ou 2 - paralizada
			if (tarefa.getStatus() == 1) {
				tarefa.setStatus(2);

				try {
					// Atualiza o status da tarefa no banco de dados.
					TarefaDAO dao = new TarefaDAO();
					dao.atualizar(tarefa);

					// lista novamente as tarefas atualizadas
					listar();
				} catch (DAOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@FXML
	public void finalizarTarefa(ActionEvent e) {

		// obtem o objeto da tabela que foi selecionado pelo usuarios se nenhum
		// objeto esta selecionado, o metodo ira retornar null.
		TarefaVO tarefa = (TarefaVO) tabTarefas.getSelectionModel().getSelectedItem();

		// verifica se uma tarefa foi selecionada
		if (tarefa != null) {

			// verifica se o status � - em aberto ou 2 - paralizada
			if (tarefa.getStatus() == 1) {
				tarefa.setStatus(4);

				try {
					// Atualiza o status da tarefa no banco de dados.
					TarefaDAO dao = new TarefaDAO();
					dao.atualizar(tarefa);

					// lista novamente as tarefas atualizadas
					listar();
				} catch (DAOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@FXML
	public void cancelarTarefa(ActionEvent e) {

		// obtem o objeto da tabela que foi selecionado pelo usuarios se nenhum
		// objeto esta selecionado, o metodo ira retornar null.
		TarefaVO tarefa = (TarefaVO) tabTarefas.getSelectionModel().getSelectedItem();

		// verifica se uma tarefa foi selecionada
		if (tarefa != null) {

			// verifica se o status � - em aberto ou 2 - paralizada
			if (tarefa.getStatus() == 0 || tarefa.getStatus() == 1 || tarefa.getStatus() == 2) {
				tarefa.setStatus(3);

				try {
					// Atualiza o status da tarefa no banco de dados.
					TarefaDAO dao = new TarefaDAO();
					dao.atualizar(tarefa);

					// lista novamente as tarefas atualizadas
					listar();
				} catch (DAOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	@FXML
	public void selecionarItemLista(MouseEvent event) {
		TarefaVO selecao = (TarefaVO) tabTarefas.getSelectionModel().getSelectedItem();
		
		if (selecao != null){
			btnIniciar.setDisable(true);
			btnParalizar.setDisable(true);
			btnCancelar.setDisable(true);
			btnFinalizar.setDisable(true);
			
			if (selecao.getStatus() == 0){
				btnIniciar.setDisable(false);
				btnCancelar.setDisable(false);
			}else if (selecao.getStatus() == 1){
				btnCancelar.setDisable(false);
				btnParalizar.setDisable(false);
				btnFinalizar.setDisable(false);
			}else if (selecao.getStatus() == 2){
				btnIniciar.setDisable(false);
				btnCancelar.setDisable(false);
				btnFinalizar.setDisable(false);
			}
		}
	}
}