package mdl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.uml2.uml.Property;

public class Grafo {
	/**
	 * Converte os componentes do dominio, para um formato ordenado de nos
	 * 
	 * @param classes
	 *            - Lista com os componentes de dominio
	 * @return
	 */
	public static List<Node> dominioToNode(List<Dominio> classes) {
		List<Node> nodeGrafo = convertDominioToNode(classes);

		return buscaEmProfundidade(nodeGrafo);
	}

	/**
	 * Algoritmo de busca em profundida do grafo
	 * 
	 * @param nodeGrafo
	 *            - Nos do grafo
	 * @return - Retorna uma lista de nós ordenados do mais distante da origem
	 *         para os mais proximo
	 */
	public static List<Node> buscaEmProfundidade(List<Node> nodeGrafo) {
		List<Node> nodeOrdenado = new ArrayList<Node>();

		for (Node node : nodeGrafo) {
			if (!node.isVisitado()) {
				node.setVisitado(true);
				visita(nodeOrdenado, node);
			}
		}

		return nodeOrdenado;
	}

	/**
	 * Percorre um novo no e os seus adjacentes, necessário para o algoritmo de
	 * busca em profundidade
	 * 
	 * @param nodeOrdenado
	 *            - Lista de nos ordenados em construcao
	 * @param node
	 *            - node a ser percorrido
	 */
	private static void visita(List<Node> nodeOrdenado, Node node) {
		for (Node nodeAdj : node.getAdjacentes()) {
			visita(nodeOrdenado, nodeAdj);
		}
		node.setVisitado(true);
		nodeOrdenado.add(node);
	}

	/**
	 * Converte os componentes do dominio para um grafo
	 * 
	 * @param classes
	 *            - Lista de componentes do dominio
	 * @return
	 */
	public static List<Node> convertDominioToNode(List<Dominio> classes) {
		List<Node> nodeGrafo = new ArrayList<Node>();

		for (Dominio domClass : classes) {
			Node node = Grafo.getNode(nodeGrafo, domClass.getClassDom()
					.getName());

			for (Property prop : domClass.getAtributos()) {
				String type = "";
				if(prop.getType().getName().equals("int")){
					type = "number";
				} else{
					type = prop.getType().getName();
				}
				
				node.getPropriedades().put(prop.getLabel(),
						type);
				if (!prop.getType().eClass().getName().toString()
						.equals("PrimitiveType")) {
					
					Node nodeAdj = Grafo.getNode(nodeGrafo, prop.getType()
							.getName());
					node.getAdjacentes().add(nodeAdj);
				}
			}
		}

		return nodeGrafo;
	}

	/**
	 * Dado uma grafo, verificar se existe um no especifico dentro dele, caso
	 * nao encontre, crie um e adicione-o ao grafo
	 * 
	 * @param nodeGrafo
	 *            - Grafo a ser pesquisado
	 * @param nome
	 *            - Nome do no a ser pesquisado
	 * @return
	 */
	private static Node getNode(List<Node> nodeGrafo, String nome) {
		Node node = buscaNode(nodeGrafo, nome);
		if (node == null) {
			node = new Node();
			node.setNome(nome);
			nodeGrafo.add(node);
		}

		return node;
	}

	/**
	 * Busca se existe um no em um grafo
	 * 
	 * @param nodeGrafo
	 * @param nome
	 * @return
	 */
	public static Node buscaNode(List<Node> nodeGrafo, String nome) {
		for (Node node : nodeGrafo) {
			if (node.getNome().equals(nome)) {
				return node;
			}
		}

		return null;
	}
}
