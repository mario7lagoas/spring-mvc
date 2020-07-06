$('#confirmacaoExclusaoModal').on(
		'show.bs.modal',
		function(event) {

			var button = $(event.relatedTarget);

			var codigoTitulo = button.data('codigo');
			var descricaoTitulo = button.data('descricao');

			var modal = $(this);

			var form = modal.find('form');

			var action = form.data('url-base');

			if (!action.endsWith('/')) {
				action += '/';
			}

			form.attr('action', action + codigoTitulo);
			modal.find('.modal-body span').html(
					'Tem certeza que deseja excluir o título <strong>'
							+ descricaoTitulo + '</strong>?');

		})

$(function() {

	$('.js-atualizar').on('click', function(event) {

		event.preventDefault();

		var botaoReceber = $(event.currentTarget);
		var urlReceber = botaoReceber.attr('href');

		var response = $.ajax({
			url : urlReceber,
			type : 'PUT'
		});
		
		response.done(function(e){
			console.log(e);
			var codigoTitulo = botaoReceber.data('codigo');
			console.log(codigoTitulo);
			$('[data-role=' + codigoTitulo + ']').html('<span class="badge-success">' + e + '</span>');
			botaoReceber.hide();
			
		});
		
		respone.fail(function(e){
			console.log(e);
			alert('Erro recebendo cobrança.');
		})

	});

	$('[rel="tooltip"]').tooltip();

	$('.js-currency').maskMoney({
		decimal : ',',
		thousands : '.'
	});

});