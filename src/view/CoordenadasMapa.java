package view;

import java.awt.Point;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Posição, em pixels da imagem imagens/tabuleiro.jpg (2905 x 2919), de cada
 * parada e de cada lugar onde o Khan pode ficar.
 *
 * Os nomes são os mesmos devolvidos pela ModelAPI (getParadas() e
 * getDestinosDoKhan()). As coordenadas ficam na View porque dependem da
 * imagem; o Model só conhece os nomes e as rotas. O PainelTabuleiro converte
 * esses pontos para a escala em que a imagem está sendo desenhada.
 */
public final class CoordenadasMapa {

	/** Dimensões da imagem original do tabuleiro. */
	public static final int LARGURA_IMAGEM = 2905;
	public static final int ALTURA_IMAGEM = 2919;

	/** Raio aproximado do círculo de uma parada simples na imagem. */
	public static final int RAIO_PARADA = 55;

	/** Nome usado pelo Model para a área de melhorias (5º lugar do Khan). */
	public static final String AREA_MELHORIAS = "AREA_MELHORIAS";

	private static final Map<String, Point> PARADAS = new HashMap<>();
	private static final Map<String, Point> LOCAIS_DO_KHAN = new HashMap<>();

	static {
		PARADAS.put("Karakorum", new Point(1445, 1290));

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

		// Cabeças do Khan e o ícone do Khan na área de melhorias
		LOCAIS_DO_KHAN.put("Khan do Noroeste", new Point(622, 622));
		LOCAIS_DO_KHAN.put("Khan do Nordeste", new Point(1743, 902));
		LOCAIS_DO_KHAN.put("Khan do Sudoeste", new Point(798, 1719));
		LOCAIS_DO_KHAN.put("Khan do Sudeste", new Point(2138, 2051));
		LOCAIS_DO_KHAN.put(AREA_MELHORIAS, new Point(111, 2802));
	}

	private CoordenadasMapa() {
	}

	/** Centro da parada na imagem; null se o nome não for de uma parada. */
	public static Point daParada(String nome) {
		return PARADAS.get(nome);
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
