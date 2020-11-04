# Timer

Projeto final de programação realizado para a disciplina de Programação Orientada a Objetos na UFABC. O projeto consiste em um timer e organizador de tarefas programado em Java, com interface gráfica construída utilizando Java FX. O projeto buscou a implementação dos conceitos básicos de orientação a objetos, padrões de projeto - como um extra, a implementação do timer tirou proveito das ferramentas de multithreading disponibilizadas pela linguagem Java.

O relatório final submetido para a conclusão da disciplina, contendo uma descrição detalhada do funcionamento do programa, pode ser lido no arquivo "relatório". A versão final e executável do programa pode ser acessada pelo arquivo ".jar".

### Proposta do Projeto

#### Motivações

A técnica pomodoro é uma técnica de produtividade e gerenciamento de tempo em que são especificados intervalos de 25 minutos para trabalho focado, seguidos de intervalos de descansos de 5 minutos, com um período maior de descanso após um número determinado de ciclos (ou pomodoros) – usualmente 15 minutos de descanso após 4 pomodoros. O tempo se dá pela constatação de que, em média, uma pessoa não treinada consegue manter sua concentração em uma atividade por aproximadamente 30 minutos, e pequenas pausas para descanso evita que se fique fadigado rapidamente. Outros parâmetros são comuns, como ciclos de trabalho/descanso com 45/15 minutos, ou 90/30 minutos.
A técnica é popular entre estudantes, músicos e profissionais e muitos aplicativos inspirados nela já foram produzidos, dos mais simples, como um simples Timer para pomodoros de 25 minutos, aos mais complexos. Entretanto, nenhum aplicativo se adequa plenamente às necessidades individuais, de modo que podem faltar funcionalidades ou a complexidade demasiada afasta usuários. Neste sentido, conhecimentos em programação dão ao indivíduo a oportunidade de construir para si, e porventura para outros, ferramentas tais que o satisfaça plenamente.

#### Objetivos

Objetiva-se construir uma ferramenta simples de gerenciamento de tempo, tendo a técnica pomodoro como base, ou padrão de parâmetros, mas possibilitando customização suficiente para que o usuário possa especificar o tempo que pretende trabalhar em um projeto, junto com seus períodos de descansos e o número de ciclos que pretende executar, gerenciados automaticamente pelo aplicativo, que o avisará quando um ciclo acaba ou termina. Dessa forma, o aplicativo pode ser utilizado para projetos distintos, com suas particularidades de gerenciamento de tempo.

#### Descrição Geral

O projeto, no seu escopo básico, possuirá:

●	Possibilidade de criação de atividades com título, descrição, tempo de duração, tempo de descanso, alarme (áudio e mensagem de texto), a serem especificados pelo usuário ou inicializados com parâmetros padrões.

●	Criação de perfil de usuário.

●	Exportação de atividades indexados por usuários, como backup, carregado quando um usuário inicia a sessão.

●	Edição, remoção e visualização de atividades do arquivo de cada usuário.

●	Ativação de atividades, com timer, com opção de pausar, encerrar e recomeçar.

●	Inclusão de atividades em uma lista de ativação automática.
