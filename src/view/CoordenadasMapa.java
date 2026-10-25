package view;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import model.ModelAPI;

/**
 * Posição, em pixels da imagem imagens/tabuleiro.jpg (2905 x 2919), de cada
 * parada e de cada lugar onde o Khan pode ficar.
 *
 * Os nomes são os mesmos devolvidos pela ModelAPI (getParadas() e
 * getLocaisDoKhan()). As coordenadas ficam na View porque dependem da
 * imagem; o Model só conhece os nomes e as rotas. O PainelTabuleiro converte
 * esses pontos para a escala em que a imagem está sendo desenhada.
 */
public final class CoordenadasMapa {

	/** Dimensões da imagem original do tabuleiro. */
	public static final int LARGURA_IMAGEM = 2905;
	public static final int ALTURA_IMAGEM = 2919;

	/** Raio aproximado do círculo de uma parada simples na imagem. */
	public static final int RAIO_PARADA = 55;

	/** Distância máxima entre o clique e o centro de um círculo de parada. */
	public static final int RAIO_CLIQUE = 70;

	public static final String KARAKORUM = "Karakorum";

	/** Karakorum é o quadro inteiro do centro do mapa, e não um círculo. */
	private static final Rectangle QUADRO_KARAKORUM = new Rectangle(1195, 1040, 500, 500);

	private static final Map<String, Point> PARADAS = new HashMap<>();
	/** Centros dos dois círculos de cada parada dupla; em PARADAS fica o ponto entre eles. */
	private static final Map<String, Point[]> CIRCULOS_DAS_DUPLAS = new HashMap<>();
	private static final Map<String, Point> LOCAIS_DO_KHAN = new HashMap<>();

	static {
		PARADAS.put(KARAKORUM, new Point(1445, 1290));

		// Rússia
		PARADAS.put("Kiev Nordeste", new Point(490, 255));
		PARADAS.put("Kiev Sudeste", new Point(471, 537));
		PARADAS.put("Kiev Sudoeste", new Point(127, 537));
		PARADAS.put("Moscou Noroeste", new Point(856, 556));
		PARADAS.put("Moscou Nordeste", new Point(1275, 490));
		PARADAS.put("Moscou Sul", new Point(1082, 833));
		PARADAS.put("Sarai Norte", new Point(1754, 228));
		PARADAS.put("Sarai Sudeste", new Point(1893, 590));
		PARADAS.put("Sarai Sudoeste", new Point(1559, 670));
		PARADAS.put("Conselheiro Norte", new Point(890, 208));
		PARADAS.put("Conselheiro Nordeste", new Point(2236, 500));

		// Pérsia
		PARADAS.put("Samarcanda Norte", new Point(843, 1193));
		PARADAS.put("Samarcanda Oeste", new Point(569, 1412));
		PARADAS.put("Samarcanda Sudeste", new Point(957, 1556));
		PARADAS.put("Bagdá Norte", new Point(216, 1494));
		PARADAS.put("Bagdá Leste", new Point(451, 1643));
		PARADAS.put("Bagdá Sul", new Point(180, 1913));
		PARADAS.put("Cabul Norte", new Point(1275, 1817));
		PARADAS.put("Cabul Leste", new Point(1475, 2053));
		PARADAS.put("Cabul Sul", new Point(1216, 2236));
		PARADAS.put("Conselheiro Oeste", new Point(462, 897));
		PARADAS.put("Conselheiro Sudoeste", new Point(726, 1883));

		// China
		PARADAS.put("Pequim Norte", new Point(2143, 1251));
		PARADAS.put("Pequim Sudoeste", new Point(1893, 1607));
		PARADAS.put("Pequim Sudeste", new Point(2172, 1676));
		PARADAS.put("Kaesong Norte", new Point(2500, 655));
		PARADAS.put("Kaesong Leste", new Point(2770, 880));
		PARADAS.put("Kaesong Sul", new Point(2528, 1075));
		PARADAS.put("Cantão Norte", new Point(2528, 2030));
		PARADAS.put("Cantão Oeste", new Point(2235, 2229));
		PARADAS.put("Cantão Sudeste", new Point(2584, 2410));
		PARADAS.put("Conselheiro Leste", new Point(2556, 1552));
		PARADAS.put("Conselheiro Sul", new Point(1835, 2222));

		// Os dois círculos de cada parada dupla
		dupla("Conselheiro Norte", 850, 170, 930, 250);
		dupla("Conselheiro Nordeste", 2180, 500, 2290, 500);
		dupla("Conselheiro Oeste", 468, 845, 455, 945);
		dupla("Conselheiro Sudoeste", 670, 1880, 785, 1890);
		dupla("Conselheiro Leste", 2556, 1500, 2556, 1605);
		dupla("Conselheiro Sul", 1780, 2215, 1893, 2228);

		// Cabeças do Khan e o ícone do Khan na área de melhorias
		LOCAIS_DO_KHAN.put("Khan do Noroeste", new Point(622, 622));
		LOCAIS_DO_KHAN.put("Khan do Nordeste", new Point(1743, 902));
		LOCAIS_DO_KHAN.put("Khan do Sudoeste", new Point(798, 1719));
		LOCAIS_DO_KHAN.put("Khan do Sudeste", new Point(2138, 2051));
		LOCAIS_DO_KHAN.put(ModelAPI.AREA_MELHORIAS, new Point(111, 2802));
	}

	private CoordenadasMapa() {
	}

	private static void dupla(String nome, int x1, int y1, int x2, int y2) {
		CIRCULOS_DAS_DUPLAS.put(nome, new Point[] { new Point(x1, y1), new Point(x2, y2) });
	}

	/** Centro da parada na imagem; null se o nome não for de uma parada. */
	public static Point daParada(String nome) {
		return PARADAS.get(nome);
	}

	/**
	 * Centros dos círculos desenhados para a parada: dois numa parada dupla, um
	 * nas demais. Vazio se o nome não for de uma parada.
	 */
	public static Point[] circulosDaParada(String nome) {
		Point[] dupla = CIRCULOS_DAS_DUPLAS.get(nome);
		if (dupla != null) {
			return dupla.clone();
		}
		Point p = PARADAS.get(nome);
		return p == null ? new Point[0] : new Point[] { p };
	}

	/** Indica se o ponto da imagem cai no quadro de Karakorum. */
	public static boolean noQuadroDeKarakorum(int x, int y) {
		return QUADRO_KARAKORUM.contains(x, y);
	}

	/** Posição do Khan na imagem; null se o nome não for um lugar do Khan. */
	public static Point doLocalDoKhan(String nome) {
		return LOCAIS_DO_KHAN.get(nome);
	}

	public static Set<String> getParadas() {
		return Collections.unmodifiableSet(PARADAS.keySet());
	}

	public static Set<String> getLocaisDoKhan() {
		return Collections.unmodifiableSet(LOCAIS_DO_KHAN.keySet());
	}
}
