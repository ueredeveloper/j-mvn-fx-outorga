USE [Outorga]

/* Tarefa: 14/05/2024
	Inserção de dados na tabela Bacias Hidrográficas 
		Obs: não vou adicionar no momento o polígono.

*/

/*
USE [Outorga]
GO

SELECT [OBJECTID_1]
      ,[bacia_nome]
      ,[shape_leng]
      ,[GDB_GEOMATTR_DATA]
      ,[bacia_cod]
      ,[Shape]
  FROM [dbo].[BACIAS_HIDROGRAFICAS]
GO
*/

/*Adasa: buscar linha por linha, convertendo o polígono para texto.

SELECT [OBJECTID_1]
      ,[bacia_nome]
      ,[shape_leng]
      ,[Shape].ToString() Shape
      ,[GDB_GEOMATTR_DATA]
      ,[bacia_cod]
  FROM [SRH].[gisadmin].[BACIAS_HIDROGRAFICAS]
  where [OBJECTID_1] = 1
  */
  
-- Procedimentos Gerais

--drop table [dbo].[Bacias_Hidrograficas]

/*
CREATE TABLE Bacias_Hidrograficas (
    bacia_ID INT IDENTITY(1, 1),
    bacia_Nome VARCHAR(70),
    bacia_Shape_Leng float,
    Shape GEOMETRY,
    GDB_GEOMATTR_DATA float,
    PRIMARY KEY (bacia_ID)
);
alter table [dbo].[Bacias_Hidrograficas] add bacia_Cod int
*/

--alter table [dbo].[BACIAS_HIDROGRAFICAS] drop column Shape
--alter table [dbo].[BACIAS_HIDROGRAFICAS] ADD Shape geometry

/*
insert into [dbo].[BACIAS_HIDROGRAFICAS] (bacia_Nome,bacia_Shape_Leng,bacia_cod)
VALUES (
   'Rio Corumbá',
   77653.84138390,	
   1
)*/

/*
insert into [dbo].[BACIAS_HIDROGRAFICAS] (bacia_Nome,bacia_Shape_Leng,bacia_cod)
VALUES ('Rio Descoberto', 186096.96270900, 3)
*/

/*
insert into [dbo].[BACIAS_HIDROGRAFICAS] (bacia_Nome,bacia_Shape_Leng,bacia_cod)
VALUES (
'Rio Paranã',
13192.10153810,
8
)*/

/*
insert into [dbo].[BACIAS_HIDROGRAFICAS] (bacia_Nome,bacia_Shape_Leng,bacia_cod)
VALUES (
'Rio São Bartolomeu',
257085.55687500,
2
)*/
/*
insert into [dbo].[BACIAS_HIDROGRAFICAS] (bacia_Nome,bacia_Shape_Leng,bacia_cod)
VALUES (
'Rio São Marcos',
30074.73120980,
 6
)*/

/*
insert into [dbo].[BACIAS_HIDROGRAFICAS] (bacia_Nome,bacia_Shape_Leng,bacia_cod)
VALUES (
'Rio Maranhão',
185527.83627300,
 4
)*/
/*
insert into [dbo].[BACIAS_HIDROGRAFICAS] (bacia_Nome,bacia_Shape_Leng,bacia_cod)
VALUES (
'Rio Paranoá',
162109.53501700,
7
)*/

/*
insert into [dbo].[BACIAS_HIDROGRAFICAS] (bacia_Nome,bacia_Shape_Leng,bacia_cod)
VALUES (
'Rio Preto',
242283.46233900,
5
)*/

