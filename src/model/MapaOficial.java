package model;

/**
 * Monta o Tabuleiro com o mapa do jogo físico.
 *
 * Transcrito da imagem oficial do tabuleiro (tabuleiro.jpg, publicada no EAD
 * para a 2ª iteração), que substitui a transcrição aproximada feita a partir do
 * manual escaneado na 1ª iteração. O mapa tem:
 *
 * - 3 regiões (Rússia, China, Pérsia) e Karakorum no centro, fora delas;
 * - 34 paradas: em cada região, 9 paradas simples (3 no anel de cada cidade)
 * e 2 paradas duplas de conselheiro; mais Karakorum;
 * - 9 cidades: Kiev, Moscou, Sarai (Rússia); Samarcanda, Bagdá, Cabul
 * (Pérsia); Pequim, Kaesong, Cantão (China);
 * - 12 províncias com ícone de tributo (4 de cada tipo). Cada uma das 4
 * cabeças do Khan fica numa província e tem setas para ela e para 2
 * vizinhas (manual, p. 9).
 *
 * Convenção de nomes das paradas, que não têm nome no jogo: "<Cidade>
 * <direção>" para as paradas do anel da cidade, com a direção em relação ao
 * centro da cidade; "Conselheiro <posição>" para as paradas duplas, com a
 * posição em relação ao mapa. As coordenadas de cada parada na imagem ficam na
 * View (view.CoordenadasMapa).
 */
final class MapaOficial {

	// Nomes públicos ao pacote, para evitar strings repetidas nos testes/controller.
	static final String KARAKORUM = "Karakorum";

	static final String CONSELHEIRO_NORTE = "Conselheiro Norte";       // Rússia, ao norte de Kiev e Moscou
	static final String CONSELHEIRO_NORDESTE = "Conselheiro Nordeste"; // Rússia, a leste de Sarai
	static final String CONSELHEIRO_OESTE = "Conselheiro Oeste";       // Pérsia, ao sul de Kiev
	static final String CONSELHEIRO_SUDOESTE = "Conselheiro Sudoeste"; // Pérsia, entre Bagdá e Cabul
	static final String CONSELHEIRO_LESTE = "Conselheiro Leste";       // China, entre Kaesong e Cantão
	static final String CONSELHEIRO_SUL = "Conselheiro Sul";           // China, entre Cabul e Cantão

	private MapaOficial() {
	}

	/** Cria um tabuleiro novo, vazio de peças, com a geografia do jogo. */
	static Tabuleiro criar() {
		Tabuleiro t = new Tabuleiro();
		criarParadas(t);
		criarRotas(t);
		criarCidades(t);
		criarProvincias(t);
		return t;
	}

	private static void criarParadas(Tabuleiro t) {
		t.adicionarParada(KARAKORUM, null, TipoParada.KARAKORUM);

		// ---- Rússia (norte)
		anel(t, Regiao.RUSSIA, "Kiev", "Nordeste", "Sudeste", "Sudoeste");
		anel(t, Regiao.RUSSIA, "Moscou", "Noroeste", "Nordeste", "Sul");
		anel(t, Regiao.RUSSIA, "Sarai", "Norte", "Sudeste", "Sudoeste");
		t.adicionarParada(CONSELHEIRO_NORTE, Regiao.RUSSIA, TipoParada.DUPLA);
		t.adicionarParada(CONSELHEIRO_NORDESTE, Regiao.RUSSIA, TipoParada.DUPLA);

		// ---- Pérsia (oeste e sudoeste)
		anel(t, Regiao.PERSIA, "Samarcanda", "Norte", "Sudeste", "Oeste");
		anel(t, Regiao.PERSIA, "Bagdá", "Norte", "Leste", "Sul");
		anel(t, Regiao.PERSIA, "Cabul", "Norte", "Leste", "Sul");
		t.adicionarParada(CONSELHEIRO_OESTE, Regiao.PERSIA, TipoParada.DUPLA);
		t.adicionarParada(CONSELHEIRO_SUDOESTE, Regiao.PERSIA, TipoParada.DUPLA);

		// ---- China (leste)
		anel(t, Regiao.CHINA, "Pequim", "Norte", "Sudeste", "Sudoeste");
		anel(t, Regiao.CHINA, "Kaesong", "Norte", "Leste", "Sul");
		anel(t, Regiao.CHINA, "Cantão", "Norte", "Sudeste", "Oeste");
		t.adicionarParada(CONSELHEIRO_LESTE, Regiao.CHINA, TipoParada.DUPLA);
		t.adicionarParada(CONSELHEIRO_SUL, Regiao.CHINA, TipoParada.DUPLA);
	}

	/** Cria as paradas "<cidade> <lado>" e as liga em ciclo (o anel da cidade). */
	private static void anel(Tabuleiro t, Regiao r, String cidade, String... lados) {
		for (String lado : lados) {
			t.adicionarParada(cidade + " " + lado, r, TipoParada.SIMPLES);
		}
		for (int i = 0; i < lados.length; i++) {
			String a = cidade + " " + lados[i];
			String b = cidade + " " + lados[(i + 1) % lados.length];
			t.ligar(a, b);
		}
	}

	private static void criarRotas(Tabuleiro t) {
		// Rússia
		t.ligar("Kiev Nordeste", CONSELHEIRO_NORTE);
		t.ligar("Kiev Nordeste", "Moscou Noroeste");
		t.ligar(CONSELHEIRO_NORTE, "Moscou Nordeste");
		t.ligar("Moscou Nordeste", "Sarai Sudoeste");
		t.ligar("Sarai Sudeste", CONSELHEIRO_NORDESTE);
		// Rússia ↔ Pérsia
		t.ligar("Kiev Sudeste", CONSELHEIRO_OESTE);
		t.ligar("Moscou Noroeste", CONSELHEIRO_OESTE);
		t.ligar("Moscou Sul", "Samarcanda Norte");
		// Rússia ↔ China
		t.ligar(CONSELHEIRO_NORDESTE, "Kaesong Norte");
		// Pérsia
		t.ligar(CONSELHEIRO_OESTE, "Samarcanda Norte");
		t.ligar(CONSELHEIRO_OESTE, "Bagdá Norte");
		t.ligar("Samarcanda Oeste", "Bagdá Norte");
		t.ligar("Bagdá Leste", CONSELHEIRO_SUDOESTE);
		t.ligar("Samarcanda Sudeste", "Cabul Norte");
		t.ligar(CONSELHEIRO_SUDOESTE, "Cabul Norte");
		// Pérsia ↔ China
		t.ligar("Cabul Norte", "Pequim Sudoeste");
		t.ligar("Cabul Leste", CONSELHEIRO_SUL);
		// China
		t.ligar("Kaesong Sul", "Pequim Norte");
		t.ligar("Kaesong Sul", CONSELHEIRO_LESTE);
		t.ligar("Pequim Sudoeste", CONSELHEIRO_SUL);
		t.ligar("Pequim Sudeste", "Cantão Norte");
		t.ligar(CONSELHEIRO_LESTE, "Cantão Norte");
		t.ligar(CONSELHEIRO_SUL, "Cantão Oeste");
		// Karakorum (centro): uma rota para cada lado
		t.ligar(KARAKORUM, "Sarai Sudoeste");
		t.ligar(KARAKORUM, CONSELHEIRO_NORDESTE);
		t.ligar(KARAKORUM, "Pequim Norte");
		t.ligar(KARAKORUM, "Samarcanda Sudeste");
	}

	private static void criarCidades(Tabuleiro t) {
		cidade(t, Regiao.RUSSIA, "Kiev", "Nordeste", "Sudeste", "Sudoeste");
		cidade(t, Regiao.RUSSIA, "Moscou", "Noroeste", "Nordeste", "Sul");
		cidade(t, Regiao.RUSSIA, "Sarai", "Norte", "Sudeste", "Sudoeste");
		cidade(t, Regiao.PERSIA, "Samarcanda", "Norte", "Sudeste", "Oeste");
		cidade(t, Regiao.PERSIA, "Bagdá", "Norte", "Leste", "Sul");
		cidade(t, Regiao.PERSIA, "Cabul", "Norte", "Leste", "Sul");
		cidade(t, Regiao.CHINA, "Pequim", "Norte", "Sudeste", "Sudoeste");
		cidade(t, Regiao.CHINA, "Kaesong", "Norte", "Leste", "Sul");
		cidade(t, Regiao.CHINA, "Cantão", "Norte", "Sudeste", "Oeste");
	}

	private static void cidade(Tabuleiro t, Regiao r, String nome, String... lados) {
		String[] paradas = new String[lados.length];
		for (int i = 0; i < lados.length; i++) {
			paradas[i] = nome + " " + lados[i];
		}
		t.adicionarCidade(nome, r, paradas);
	}

	/**
	 * Cada província é a área entre rotas onde está o ícone de tributo; as
	 * paradas listadas são as que contornam essa área.
	 */
	private static void criarProvincias(Tabuleiro t) {
		// Rússia
		Provincia khanNO = t.adicionarProvincia("Khan do Noroeste", Regiao.RUSSIA, TipoTributo.YURT, true,
				"Kiev Nordeste", "Kiev Sudeste", CONSELHEIRO_OESTE, "Moscou Noroeste");
		t.adicionarProvincia("Estepe de Moscou", Regiao.RUSSIA, TipoTributo.ESPADA, false,
				"Kiev Nordeste", CONSELHEIRO_NORTE, "Moscou Nordeste", "Moscou Noroeste");
		t.adicionarProvincia("Planície de Moscou", Regiao.RUSSIA, TipoTributo.MOEDA, false,
				CONSELHEIRO_OESTE, "Moscou Noroeste", "Moscou Sul", "Samarcanda Norte");
		Provincia khanNE = t.adicionarProvincia("Khan do Nordeste", Regiao.RUSSIA, TipoTributo.MOEDA, true,
				"Sarai Sudoeste", "Sarai Sudeste", CONSELHEIRO_NORDESTE, KARAKORUM);
		t.adicionarProvincia("Estepe Central", Regiao.RUSSIA, TipoTributo.YURT, false,
				"Moscou Nordeste", "Sarai Sudoeste", KARAKORUM, "Samarcanda Sudeste", "Samarcanda Norte",
				"Moscou Sul");
		// China
		t.adicionarProvincia("Vale de Kaesong", Regiao.CHINA, TipoTributo.ESPADA, false,
				CONSELHEIRO_NORDESTE, "Kaesong Norte", "Kaesong Sul", "Pequim Norte", KARAKORUM);
		Provincia khanSE = t.adicionarProvincia("Khan do Sudeste", Regiao.CHINA, TipoTributo.YURT, true,
				"Pequim Sudoeste", "Pequim Sudeste", "Cantão Norte", "Cantão Oeste", CONSELHEIRO_SUL);
		t.adicionarProvincia("Colinas de Pequim", Regiao.CHINA, TipoTributo.MOEDA, false,
				"Pequim Norte", "Pequim Sudeste", "Cantão Norte", CONSELHEIRO_LESTE, "Kaesong Sul");
		// Pérsia
		t.adicionarProvincia("Deserto de Samarcanda", Regiao.PERSIA, TipoTributo.ESPADA, false,
				CONSELHEIRO_OESTE, "Bagdá Norte", "Samarcanda Oeste", "Samarcanda Norte");
		Provincia khanSO = t.adicionarProvincia("Khan do Sudoeste", Regiao.PERSIA, TipoTributo.MOEDA, true,
				"Samarcanda Oeste", "Samarcanda Sudeste", "Cabul Norte", CONSELHEIRO_SUDOESTE, "Bagdá Leste",
				"Bagdá Norte");
		t.adicionarProvincia("Oásis Central", Regiao.PERSIA, TipoTributo.YURT, false,
				KARAKORUM, "Samarcanda Sudeste", "Cabul Norte", "Pequim Sudoeste", "Pequim Norte");
		t.adicionarProvincia("Montanhas de Cabul", Regiao.PERSIA, TipoTributo.ESPADA, false,
				"Cabul Norte", "Pequim Sudoeste", CONSELHEIRO_SUL, "Cabul Leste");

		// Setas de cada cabeça do Khan: além da própria província, 2 vizinhas.
		khanNO.adicionarVizinhaDoKhan(t.getProvincia("Estepe de Moscou"));
		khanNO.adicionarVizinhaDoKhan(t.getProvincia("Planície de Moscou"));
		khanNE.adicionarVizinhaDoKhan(t.getProvincia("Estepe Central"));
		khanNE.adicionarVizinhaDoKhan(t.getProvincia("Vale de Kaesong"));
		khanSO.adicionarVizinhaDoKhan(t.getProvincia("Deserto de Samarcanda"));
		khanSO.adicionarVizinhaDoKhan(t.getProvincia("Oásis Central"));
		khanSE.adicionarVizinhaDoKhan(t.getProvincia("Montanhas de Cabul"));
		khanSE.adicionarVizinhaDoKhan(t.getProvincia("Colinas de Pequim"));

		// Preparação (manual, p. 4, item 7): 1 peça de tributo em cada província.
		for (Provincia p : t.getProvincias()) {
			p.colocarPeca();
		}
	}
}
