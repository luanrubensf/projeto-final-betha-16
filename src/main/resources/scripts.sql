/**
 * Author:  Rubens
 * Created: 05/11/2016
 */

-- ================================================ CATEGORIAS ================================================ 

-- Table: public.categorias

-- DROP TABLE public.categorias;

CREATE TABLE public.categorias
(
    id bigint NOT NULL,
    descricao character varying(100) COLLATE "default".pg_catalog,
    CONSTRAINT categorias_pkey PRIMARY KEY (id),
    CONSTRAINT categorias_id_check CHECK (id > 0)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.categorias
    OWNER to postgres;

-- ================================================ GAMES ================================================ 

-- Table: public.games

-- DROP TABLE public.games;

CREATE TABLE public.games
(
    id bigint NOT NULL,
    nome character varying(150) COLLATE "default".pg_catalog NOT NULL,
    descricao character varying(300) COLLATE "default".pg_catalog,
    ano integer,
    finalizado boolean NOT NULL,
    idcategoria bigint,
    CONSTRAINT games_pkey PRIMARY KEY (id),
    CONSTRAINT fkcategoria FOREIGN KEY (idcategoria)
        REFERENCES public.categorias (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT games_id_check CHECK (id > 0)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.games
    OWNER to postgres;

-- Trigger: total_finalizados

-- DROP TRIGGER total_finalizados ON public.games;

CREATE TRIGGER total_finalizados
    AFTER INSERT OR UPDATE 
    ON public.games
    FOR EACH STATEMENT
    EXECUTE PROCEDURE calcula_total();

--================================================ EMPRESTIMOS ================================================

-- Table: public.emprestimos

-- DROP TABLE public.emprestimos;

CREATE TABLE public.emprestimos
(
    id bigint NOT NULL,
    destino character varying(150) COLLATE "default".pg_catalog NOT NULL,
    observacao character varying(400) COLLATE "default".pg_catalog,
    idgame bigint NOT NULL,
    emissao timestamp without time zone NOT NULL,
    devolucao timestamp without time zone,
    CONSTRAINT emprestimo_pkey PRIMARY KEY (id),
    CONSTRAINT fkgame FOREIGN KEY (idgame)
        REFERENCES public.games (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT emprestimo_id_check CHECK (id > 0)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.emprestimos
    OWNER to postgres;

-- ================================================ DADOS GERAIS ================================================ 
-- Table: public.dadosgerais

-- DROP TABLE public.dadosgerais;

CREATE TABLE public.dadosgerais
(
    id bigint NOT NULL,
    qtafinalizados bigint NOT NULL,
    CONSTRAINT dadosgerais_id_check CHECK (id > 0)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.dadosgerais
    OWNER to postgres;



/* ================================================ TRIGGER ================================================ */
CREATE OR REPLACE FUNCTION calcula_total() RETURNS trigger AS $$
    DECLARE vl_total NUMERIC(12,5);
    BEGIN
        SELECT COUNT(*) INTO vl_total FROM games WHERE finalizado = true;
        UPDATE DADOSGERAIS SET QTAFINALIZADOS = vl_total;
        RETURN NEW;
    END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS total_finalizados ON games;

CREATE TRIGGER total_finalizados AFTER UPDATE OR INSERT ON games
    FOR EACH STATEMENT EXECUTE PROCEDURE calcula_total();










