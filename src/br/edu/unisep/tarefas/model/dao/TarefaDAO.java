package br.edu.unisep.tarefas.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.unisep.exception.DAOException;
import br.edu.unisep.model.dao.DAOGenerico;
import br.edu.unisep.tarefas.model.vo.TarefaVO;

public class TarefaDAO extends DAOGenerico {

	public TarefaDAO() {
		/*
		 * Executa o método construtor da superclasse (DAOGenerico), passando o
		 * nome do banco de dados tarefas
		 */
		super("tarefas");
	}

	public void salvar(TarefaVO tarefa) throws DAOException {

		try {
			Connection con = conectar();

			PreparedStatement ps = con
					.prepareStatement("INSERT INTO tarefa(ds_tarefa, dt_tarefa, tp_status) VALUES (?, ?, ?)");

			ps.setString(1, tarefa.getDescricao());

			Date dtTarefa = new Date(tarefa.getData().getTime());
			ps.setDate(2, dtTarefa);

			ps.setInt(3, tarefa.getStatus());

			ps.executeUpdate();

			ps.close();
			con.close();

		} catch (SQLException e) {

			DAOException ex = new DAOException("Erro ao inserir uma tarefa. ",
					e);
			throw ex;
		}
	}

	public List<TarefaVO> listar() throws DAOException {
		try {

			Connection con = conectar();

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from tarefa");

			List<TarefaVO> lista = new ArrayList<TarefaVO>();

			while (rs.next()) {

				TarefaVO tarefa = new TarefaVO();

				Integer id = rs.getInt("id_tarefa");
				tarefa.setId(id);

				String descricao = rs.getString("ds_tarefa");
				tarefa.setDescricao(descricao);

				Date data = rs.getDate("dt_tarefa");
				java.util.Date dtTarefa = new java.util.Date(data.getTime());
				tarefa.setData(dtTarefa);

				Integer status = rs.getInt("tp_status");
				tarefa.setStatus(status);

				lista.add(tarefa);
			}

			rs.close();
			st.close();
			con.close();

			return lista;

		} catch (SQLException e) {
			DAOException ex = new DAOException("Erro ao listar tarefas. ", e);
			throw ex;
		}
	}

	public void atualizar(TarefaVO tarefa) throws DAOException {
		try {

			Connection con = conectar();

			PreparedStatement ps = con
					.prepareStatement("update tarefa set tp_status = ? where id_tarefa = ?");

			ps.setInt(1, tarefa.getStatus());
			ps.setInt(2, tarefa.getId());

			ps.executeUpdate();

			ps.close();
			con.close();

		} catch (SQLException e) {
			DAOException ex = new DAOException("Erro ao atualizar a tarefa", e);
			throw ex;
		}
	}
}