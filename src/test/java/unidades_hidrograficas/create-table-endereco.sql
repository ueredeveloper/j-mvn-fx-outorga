CREATE TABLE [dbo].[Endereco](
	[end_ID] [int] IDENTITY(1,1) NOT NULL,
	[end_Atualizacao] [datetime2](7) NULL,
	[end_CEP] [varchar](20) NULL,
	[end_Cidade] [varchar](20) NULL,
	[end_DD_Latitude] [float] NULL,
	[end_DD_Longitude] [float] NULL,
	[end_Geom] [geometry] NULL,
	[end_Logradouro] [varchar](150) NULL,
	[end_UF] [varchar](2) NULL,
	[end_RA_FK] [int] NULL,
	[end_Usuario_FK] [int] NULL,
	[end_Croqui] [geometry] NULL,
PRIMARY KEY CLUSTERED 
(
	[end_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
)