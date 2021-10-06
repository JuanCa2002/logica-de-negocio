CREATE TABLE public.tbl_categorias (
	id_categoria int8 NOT NULL,
	nombre varchar(255) NULL,
	CONSTRAINT tbl_categorias_pkey PRIMARY KEY (id_categoria)
);

CREATE TABLE public.tbl_ciudades (
	id_ciudad int8 NOT NULL,
	nombre varchar(255) NULL,
	CONSTRAINT tbl_ciudades_pkey PRIMARY KEY (id_ciudad)
);

CREATE TABLE public.tbl_products (
	id_producto varchar(255) NOT NULL,
	marca varchar(255) NULL,
	nombre varchar(255) NULL,
	id_categoria int8 NULL,
	CONSTRAINT tbl_products_pkey PRIMARY KEY (id_producto),
	CONSTRAINT fkdjuujcdb0yxo2je56ng66phuo FOREIGN KEY (id_categoria) REFERENCES tbl_categorias(id_categoria)
);

CREATE TABLE public.tbl_tienda (
	id_tienda int8 NOT NULL,
	direccion varchar(255) NULL,
	nombre varchar(255) NULL,
	id_ciudad int8 NULL,
	CONSTRAINT tbl_tienda_pkey PRIMARY KEY (id_tienda),
	CONSTRAINT fktcmxd7e4wlovo43oyy8au3gbn FOREIGN KEY (id_ciudad) REFERENCES tbl_ciudades(id_ciudad)
);

CREATE TABLE public.tbl_usuarios (
	id_usuario varchar(255) NOT NULL,
	direccion varchar(255) NULL,
	apellido varchar(255) NULL,
	nombre varchar(255) NULL,
	id_ciudad int8 NULL,
	CONSTRAINT tbl_usuarios_pkey PRIMARY KEY (id_usuario),
	CONSTRAINT fkoe7o5ej2519taqa064jrscaig FOREIGN KEY (id_ciudad) REFERENCES tbl_ciudades(id_ciudad)
);

CREATE TABLE public.tbl_productos_tienda (
	id int8 NOT NULL,
	precio float8 NULL,
	cantidad int4 NULL,
	id_producto varchar(255) NULL,
	id_tienda int8 NULL,
	CONSTRAINT tbl_productos_tienda_pkey PRIMARY KEY (id),
	CONSTRAINT fknn6m3ox3178g9p0r4rf8qumq2 FOREIGN KEY (id_producto) REFERENCES tbl_products(id_producto),
	CONSTRAINT fksq4hpde7olql95cs6buiuspms FOREIGN KEY (id_tienda) REFERENCES tbl_tienda(id_tienda)
);