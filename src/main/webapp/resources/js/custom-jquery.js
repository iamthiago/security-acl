$(document).ready(function() {
	$('#loading').dialog({ autoOpen: false });
});

function getContainer(data) {
	$('#mainContainer').empty();
	$('#mainContainer').append(data);
}

function resultMessageModal(data) {
	$('#resultMessageModal').empty();
	$('#resultMessageModal').append('<div>'+ data.value +'</div>');
	
	$('#resultMessageModal').dialog({
		dialogClass: 'no-close',
		autoOpen: false,
		modal: true,
		draggable: false,
		resizable: false,
		height: 170,
		width: 550,
		title: data.key,
		buttons : [{
			text : "Ok",
			click : function() {
				$(this).dialog("close");
			}
		}]
	}).dialog('open');
}

function showLoading() {
	$('#loading').dialog({
		dialogClass: 'no-close',
		closeOnEscape: false,
		autoOpen: false,
		modal: true,
		draggable: false,
		resizable: false,
		height: 250,
		width: 550
	}).dialog('open');
}

function hideLoading() {
	$('#loading').dialog("destroy");
	$('#loading').dialog({ autoOpen: false });
}

function setBootstrapFields() {
	$('div.dataTables_length select').addClass('form-control input-sm');
	$('div.dataTables_filter input').addClass('form-control input-sm');
}