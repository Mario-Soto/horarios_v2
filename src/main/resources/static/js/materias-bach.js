$("select").on("change", (e) => {
	const input = e.target;
	const grupo = input.id.split("-")[1];
	const valor = input.value;
	console.log(grupo + " " + valor);
	let url = "/peticion/propedeuticos/" + grupo + "/" + valor;
	$.ajax({
		type: "GET",
		url: url,
		success: function (response) {
			for (let i = 0; i < response.materias.length; i++) {
				$("#gpo-" + response.grupo + "-prope-" + response.materias[i].materia_origen).text(
					"PROP: "+response.materias[i].materia_nombre
				);
			}
		},
	});
});
