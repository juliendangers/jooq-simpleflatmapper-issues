ALTER TABLE "Operator" DISABLE TRIGGER ALL;

INSERT INTO "Operator" ("idOperator", "supportedTypes") VALUES ('greaterThan', '{numeric}');
INSERT INTO "Operator" ("idOperator", "supportedTypes") VALUES ('equals', '{text,numeric}');
INSERT INTO "Operator" ("idOperator", "supportedTypes") VALUES ('lesserThan', '{numeric}');


ALTER TABLE "Operator" ENABLE TRIGGER ALL;

ALTER TABLE "Questionnaire" DISABLE TRIGGER ALL;

INSERT INTO "Questionnaire" ("idQuestionnaire", nom, version, action, "idQuestion_first", active, "dateCreation") VALUES ('0a2e068d-96fd-4eb9-b3ea-c4143df36a38', 'test', 1, 'chauffage', 'cab5e408-7264-4d0b-bfec-c2946439afe6', true, '2018-01-16 14:00:40.242333+00');


ALTER TABLE "Questionnaire" ENABLE TRIGGER ALL;


ALTER TABLE "TypeQuestion" DISABLE TRIGGER ALL;

INSERT INTO "TypeQuestion" ("idTypeQuestion", libelle, "nombreReponseMin", "nombreReponseMax", "memeQuestionSuivante", "definitionDeVariable", "variableType", "valeurParDefaut") VALUES (1, 'SelectionSimple', 2, 10, false, true, 'boolean', 'false');
INSERT INTO "TypeQuestion" ("idTypeQuestion", libelle, "nombreReponseMin", "nombreReponseMax", "memeQuestionSuivante", "definitionDeVariable", "variableType", "valeurParDefaut") VALUES (3, 'SelectionMultipleQuantite', 2, 10, true, true, 'numeric', '0');
INSERT INTO "TypeQuestion" ("idTypeQuestion", libelle, "nombreReponseMin", "nombreReponseMax", "memeQuestionSuivante", "definitionDeVariable", "variableType", "valeurParDefaut") VALUES (4, 'BranchementConditionnel', 2, 2, false, false, NULL, NULL);
INSERT INTO "TypeQuestion" ("idTypeQuestion", libelle, "nombreReponseMin", "nombreReponseMax", "memeQuestionSuivante", "definitionDeVariable", "variableType", "valeurParDefaut") VALUES (2, 'SelectionMultiple', 2, 10, true, true, 'boolean', 'false');


ALTER TABLE "TypeQuestion" ENABLE TRIGGER ALL;

ALTER TABLE "Question" DISABLE TRIGGER ALL;

INSERT INTO "Question" ("idQuestion", key, description, "idTypeQuestion", "idQuestionnaire", "genereNouvelleBranche", "estValide", "dateCreation", "dateMiseAJour") VALUES ('cab5e408-7264-4d0b-bfec-c2946439afe6', 'Q1', 'Question 1', 1, '0a2e068d-96fd-4eb9-b3ea-c4143df36a38', true, true, '2018-01-16 14:07:20.47062+00', '2018-02-02 09:03:48.827494+00');
INSERT INTO "Question" ("idQuestion", key, description, "idTypeQuestion", "idQuestionnaire", "genereNouvelleBranche", "estValide", "dateCreation", "dateMiseAJour") VALUES ('408145ac-5dfd-478c-a24f-4c9882175ef4', 'Q3', 'Dernière question', 1, '0a2e068d-96fd-4eb9-b3ea-c4143df36a38', false, true, '2018-01-16 14:07:20.47062+00', '2018-02-02 09:03:48.827494+00');
INSERT INTO "Question" ("idQuestion", key, description, "idTypeQuestion", "idQuestionnaire", "genereNouvelleBranche", "estValide", "dateCreation", "dateMiseAJour") VALUES ('ec40c840-c461-4fa5-bb1e-74b4670babdb', 'Q2bis', 'Question 2bis', 1, '0a2e068d-96fd-4eb9-b3ea-c4143df36a38', false, true, '2018-01-16 14:07:20.47062+00', '2018-02-02 09:03:48.827494+00');
INSERT INTO "Question" ("idQuestion", key, description, "idTypeQuestion", "idQuestionnaire", "genereNouvelleBranche", "estValide", "dateCreation", "dateMiseAJour") VALUES ('17df0c26-c4fc-4cae-b1cf-374d69d8a1e4', 'Q2', 'Question 2', 3, '0a2e068d-96fd-4eb9-b3ea-c4143df36a38', false, true, '2018-01-16 14:07:20.47062+00', '2018-02-02 09:03:48.827494+00');


ALTER TABLE "Question" ENABLE TRIGGER ALL;

ALTER TABLE "Reponse" DISABLE TRIGGER ALL;

INSERT INTO "Reponse" ("idReponse", libelle, key, "idQuestion", "idQuestion_suivante", "dateCreation", "dateMiseAJour") VALUES ('93182cb9-946e-421a-a854-4a0de51b00e5', 'Q2_1', 'Q2_1', '17df0c26-c4fc-4cae-b1cf-374d69d8a1e4', '408145ac-5dfd-478c-a24f-4c9882175ef4', '2018-01-16 14:09:47.230174+00', '2018-01-16 14:09:47.230174+00');
INSERT INTO "Reponse" ("idReponse", libelle, key, "idQuestion", "idQuestion_suivante", "dateCreation", "dateMiseAJour") VALUES ('906dd681-c134-46f7-899a-7dfcb373bd27', 'Q2_2', 'Q2_2', '17df0c26-c4fc-4cae-b1cf-374d69d8a1e4', '408145ac-5dfd-478c-a24f-4c9882175ef4', '2018-01-16 14:09:47.230174+00', '2018-01-16 14:09:47.230174+00');
INSERT INTO "Reponse" ("idReponse", libelle, key, "idQuestion", "idQuestion_suivante", "dateCreation", "dateMiseAJour") VALUES ('eab9b86f-fd08-415d-9735-360f951528ee', 'Q2_3', 'Q2_3', '17df0c26-c4fc-4cae-b1cf-374d69d8a1e4', '408145ac-5dfd-478c-a24f-4c9882175ef4', '2018-01-16 14:09:47.230174+00', '2018-01-16 14:09:47.230174+00');
INSERT INTO "Reponse" ("idReponse", libelle, key, "idQuestion", "idQuestion_suivante", "dateCreation", "dateMiseAJour") VALUES ('da03175b-ec87-42d7-83ac-236d3dbf1a9a', 'Q2bis_1', 'oui', 'ec40c840-c461-4fa5-bb1e-74b4670babdb', '408145ac-5dfd-478c-a24f-4c9882175ef4', '2018-01-16 14:09:47.230174+00', '2018-01-16 14:09:47.230174+00');
INSERT INTO "Reponse" ("idReponse", libelle, key, "idQuestion", "idQuestion_suivante", "dateCreation", "dateMiseAJour") VALUES ('0782c724-3037-458e-b277-fcec79a64027', 'Q2bis_2', 'mayBe', 'ec40c840-c461-4fa5-bb1e-74b4670babdb', '408145ac-5dfd-478c-a24f-4c9882175ef4', '2018-01-16 14:09:47.230174+00', '2018-01-16 14:40:32.36861+00');
INSERT INTO "Reponse" ("idReponse", libelle, key, "idQuestion", "idQuestion_suivante", "dateCreation", "dateMiseAJour") VALUES ('d426dc20-a850-4ff9-b2bb-91add996c3fc', 'Q3_ok', 'ok', '408145ac-5dfd-478c-a24f-4c9882175ef4', NULL, '2018-01-16 17:26:15.687218+00', '2018-01-16 17:26:15.687218+00');
INSERT INTO "Reponse" ("idReponse", libelle, key, "idQuestion", "idQuestion_suivante", "dateCreation", "dateMiseAJour") VALUES ('df00cae4-03af-4a7d-9c97-16cf4fac2267', 'Q3_nok', 'nok', '408145ac-5dfd-478c-a24f-4c9882175ef4', NULL, '2018-01-16 17:26:15.687218+00', '2018-01-16 17:26:15.687218+00');
INSERT INTO "Reponse" ("idReponse", libelle, key, "idQuestion", "idQuestion_suivante", "dateCreation", "dateMiseAJour") VALUES ('427a5c0c-e4bb-4d4d-a537-2b34f0dd7d3e', 'Q1_1', 'Reponse 1 Question 1', 'cab5e408-7264-4d0b-bfec-c2946439afe6', '17df0c26-c4fc-4cae-b1cf-374d69d8a1e4', '2018-01-16 14:09:47.230174+00', '2018-02-02 09:05:07.268675+00');
INSERT INTO "Reponse" ("idReponse", libelle, key, "idQuestion", "idQuestion_suivante", "dateCreation", "dateMiseAJour") VALUES ('785394f2-7af0-4e81-9e7c-68ee77efdbb2', 'Q1_2', 'Reponse 2 Question 1', 'cab5e408-7264-4d0b-bfec-c2946439afe6', 'ec40c840-c461-4fa5-bb1e-74b4670babdb', '2018-01-16 14:09:47.230174+00', '2018-02-02 09:06:02.368312+00');


ALTER TABLE "Reponse" ENABLE TRIGGER ALL;

ALTER TABLE "ReponseValidation" DISABLE TRIGGER ALL;

INSERT INTO "ReponseValidation" ("idReponseValidation", "idReponse", "idOperator", variables, value, type, "dateCreation", "dateMiseAJour") VALUES ('02fa7634-2f88-4a77-a748-ae04ba51f0e0', '906dd681-c134-46f7-899a-7dfcb373bd27', 'equals', '{Q1_2}', 'true', 'condition', '2018-01-22 14:17:37.527159+00', '2018-01-22 14:17:37.527159+00');


ALTER TABLE "ReponseValidation" ENABLE TRIGGER ALL;
