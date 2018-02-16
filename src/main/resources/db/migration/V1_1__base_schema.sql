CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE OR REPLACE FUNCTION set_date_maj()
  RETURNS TRIGGER AS
$func$
BEGIN
  NEW."dateMiseAJour" := now() AT TIME ZONE 'utc';
  RETURN NEW;
END
$func$ LANGUAGE plpgsql;


CREATE TABLE "Questionnaire" (
  "idQuestionnaire" UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  "nom" TEXT NOT NULL,
  "version" NUMERIC NOT NULL DEFAULT 1,
  "action" TEXT NOT NULL,
  "idQuestion_first" UUID,
  "active" BOOLEAN NOT NULL DEFAULT FALSE,
  "dateCreation" TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE UNIQUE INDEX idx_questionnaire_unique_active ON "Questionnaire" ("action", "active") WHERE ("active" IS TRUE);

ALTER TABLE "Questionnaire"
  ADD CONSTRAINT "idx_unique_questionnaire_idQuestionnaire_version"
UNIQUE ("action", "version");

CREATE TABLE "Question" (
  "idQuestion" UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  "key" TEXT NOT NULL,
  "description" TEXT NOT NULL,
  "idTypeQuestion" NUMERIC NOT NULL,
  "idQuestionnaire" UUID NOT NULL,
  "genereNouvelleBranche" BOOLEAN NOT NULL DEFAULT FALSE,
  "estValide" BOOLEAN NOT NULL DEFAULT FALSE,
  "dateCreation" TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  "dateMiseAJour" TIMESTAMP WITH TIME ZONE
);

CREATE TRIGGER tr_question_update_datemaj
  BEFORE UPDATE ON "Question"
  FOR EACH ROW EXECUTE PROCEDURE set_date_maj();

ALTER TABLE "Questionnaire"
  ADD CONSTRAINT "fk_questionnaire_question_id_first"
FOREIGN KEY ("idQuestion_first")
REFERENCES "Question" ("idQuestion");

ALTER TABLE "Question"
  ADD CONSTRAINT "fk_question_questionnaire_id"
FOREIGN KEY ("idQuestionnaire")
REFERENCES "Questionnaire" ("idQuestionnaire");

CREATE TABLE "TypeQuestion" (
  "idTypeQuestion" NUMERIC PRIMARY KEY,
  "libelle" TEXT NOT NULL,
  "nombreReponseMin" NUMERIC NOT NULL DEFAULT 1,
  "nombreReponseMax" NUMERIC,
  "memeQuestionSuivante" BOOLEAN NOT NULL DEFAULT FALSE,
  "definitionDeVariable" BOOLEAN NOT NULL DEFAULT TRUE,
  "variableType" TEXT,
  "valeurParDefaut" TEXT
);

ALTER TABLE "Question"
  ADD CONSTRAINT "fk_question_typequestion_id"
FOREIGN KEY ("idTypeQuestion")
REFERENCES "TypeQuestion" ("idTypeQuestion");

CREATE TABLE "Reponse" (
  "idReponse" UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  "libelle" TEXT NOT NULL,
  "key" TEXT NOT NULL,
  "idQuestion" UUID NOT NULL,
  "idQuestion_suivante" UUID,
  "dateCreation" TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  "dateMiseAJour" TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE TRIGGER tr_reponse_update_datemaj
  BEFORE UPDATE ON "Reponse"
  FOR EACH ROW EXECUTE PROCEDURE set_date_maj();

ALTER TABLE "Reponse"
  ADD CONSTRAINT "fk_reponse_question_id"
FOREIGN KEY ("idQuestion")
REFERENCES "Question" ("idQuestion");

ALTER TABLE "Reponse"
  ADD CONSTRAINT "fk_reponse_question_suivante_id"
FOREIGN KEY ("idQuestion_suivante")
REFERENCES "Question" ("idQuestion");

CREATE TABLE "Operator" (
  "idOperator" TEXT PRIMARY KEY,
  "supportedTypes" TEXT[] NOT NULL
);

CREATE TYPE reponseValidationType AS ENUM ('condition', 'validation');

CREATE TABLE "ReponseValidation" (
  "idReponseValidation" UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  "idReponse" UUID NOT NULL,
  "idOperator" TEXT NOT NULL,
  "variables" TEXT[] NOT NULL,
  "value" TEXT NOT NULL,
  "type" reponseValidationType NOT NULL,
  "dateCreation" TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  "dateMiseAJour" TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

ALTER TABLE "ReponseValidation"
  ADD CONSTRAINT "fk_reponsevalidation_reponse_id"
FOREIGN KEY ("idReponse")
REFERENCES "Reponse" ("idReponse");

ALTER TABLE "ReponseValidation"
  ADD CONSTRAINT "fk_reponsevalidation_operator_id"
FOREIGN KEY ("idOperator")
REFERENCES "Operator" ("idOperator");
