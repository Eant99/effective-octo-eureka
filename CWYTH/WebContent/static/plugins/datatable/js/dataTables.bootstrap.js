/*! DataTables Bootstrap 3 integration
 * ©2011-2015 SpryMedia Ltd - datatables.net/license
 */

/**
 * DataTables integration for Bootstrap 3. This requires Bootstrap 3 and
 * DataTables 1.10 or newer.
 *
 * This file sets the defaults and adds options to DataTables to style its
 * controls using Bootstrap. See http://datatables.net/manual/styling/bootstrap
 * for further information.
 */
(function( factory ){
	if ( typeof define === 'function' && define.amd ) {
		// AMD
		define( ['jquery', 'datatables.net'], function ( $ ) {
			return factory( $, window, document );
		} );
	}
	else if ( typeof exports === 'object' ) {
		// CommonJS
		module.exports = function (root, $) {
			if ( ! root ) {
				root = window;
			}

			if ( ! $ || ! $.fn.dataTable ) {
				// Require DataTables, which attaches to jQuery, including
				// jQuery if needed and have a $ property so we can access the
				// jQuery object that is used
				$ = require('datatables.net')(root, $).$;
			}

			return factory( $, root, root.document );
		};
	}
	else {
		// Browser
		factory( jQuery, window, document );
	}
}(function( $, window, document, undefined ) {
'use strict';
var DataTable = $.fn.dataTable;


/* Set the defaults for DataTables initialisation */
$.extend( true, DataTable.defaults, {
	"processing" : true, //DataTables载入数据时，是否显示‘进度’提示
	"serverSide": true,
    "language":{
        "sProcessing":   "处理中...",
        "sLengthMenu":   "每页 _MENU_条记录",
        "sZeroRecords":  "没有匹配记录",
        //"sInfo":         "第_PAGE_页/共_PAGES_页(总_MAX_条记录)",
        "sInfo":         "共_MAX_条记录",
        "sInfoEmpty":    "暂无记录",
        "sInfoFiltered": "(由 _MAX_条记录过滤)",
        "sInfoPostFix":  "",
        "sSearch":       "搜索:",
        "sUrl":          "",
        "sEmptyTable":     "暂无数据",
        "sLoadingRecords": "数据载入中...",
        "sInfoThousands":  ",",
        "oPaginate": {
            "sFirst":    "首页",
            "sPrevious": "上页",
            "sNext":     "下页",
            "sLast":     "末页",
            "jump":	 "跳转"
        },
        "oAria": {
            "sSortAscending":  ": 以升序排列此列",
            "sSortDescending": ": 以降序排列此列"
        }
    },
	"dom":"<'row'<'col-md-6 col-sm-6 col-xs-6'il><'col-md-6 col-sm-6 col-xs-6'p>>t<'row'<'col-md-6 col-sm-6 col-xs-6'><'col-md-6 col-sm-6 col-xs-6'p>>",
	renderer: 'bootstrap'
});


/* Default class modification */
$.extend( DataTable.ext.classes, {
	sWrapper:      "dataTables_wrapper dt-bootstrap",
	sFilterInput:  "form-control input-sm",
	sLengthSelect: "form-control input-sm",
	sProcessing:   "dataTables_processing panel panel-default"
});


/* Bootstrap paging button renderer */
DataTable.ext.renderer.pageButton.bootstrap = function (settings, host, idx, buttons, page, pages) {
	var api = new DataTable.Api(settings);
	var classes = settings.oClasses;
	var lang = settings.oLanguage.oPaginate;
	var aria = settings.oLanguage.oAria.paginate||{};
	var btnDisplay, btnClass, counter=0;
	//buttons为分页页码及上一页下一页的数组类
	var attach = function( container, buttons ) {
		var i, ien, node, button;
		var clickHandler = function (e) {
			e.preventDefault();
			if (!$(e.currentTarget).hasClass('disabled')&&api.page() != e.data.action){
				api.page( e.data.action ).draw('page');
			}
		};
		for ( i=0, ien=buttons.length ; i<ien ; i++ ) {
			button = buttons[i];
			if ($.isArray( button )) {
				attachs(container,button);
			} else {
				btnDisplay = '';
				btnClass = '';
				switch ( button ) {
					case 'ellipsis':
						btnDisplay = '&#x2026;';
						btnClass = 'disabled';
						break;
					case 'first':
						btnDisplay = lang.sFirst;
						btnClass = button + (page > 0 ? '' : ' disabled');
						break;
					case 'previous':
						btnDisplay = lang.sPrevious;
						btnClass = button + (page > 0 ? '' : ' disabled');
						break;
					case 'next':
						btnDisplay = lang.sNext;
						btnClass = button + (page < pages-1 ? '' : ' disabled');
						break;
					case 'last':
						btnDisplay = lang.sLast;
						btnClass = button + (page < pages-1 ? '' : ' disabled');
						break;
					default:
						btnDisplay = button + 1;
						btnClass = page === button ? 'active' : '';
						break;
				}
				if (btnDisplay) {
					node = $('<li>',{
							'class': classes.sPageButton+' '+btnClass,
							'id': idx === 0 && typeof button === 'string'?settings.sTableId +'_'+ button : null
					}).append($('<a>', {
							'href': '#',
							'aria-controls': settings.sTableId,
							'aria-label': aria[button],
							'data-dt-idx': counter,
							'tabindex': settings.iTabIndex
					}).html(btnDisplay)
					).appendTo(container);
					settings.oApi._fnBindAction(
						node, {action: button}, clickHandler
					);
					counter++;
				}
			}
		}

		/*添加跳页功能*/
		var inputPageJump = $('<input>', {
			'type': "number",
			'min': 1,
			'max': pages,
			'class': "form-control"
		}).on("keyup",function(event){
			if (event.keyCode == 13) {
				var curr = this.value.replace(/\s|\D/g, '') | 0;
				if (curr) {
					var pages = api.page.info().pages;
					curr = curr > pages ? pages : curr;
					curr--;
					api.page(curr).draw(false);
				}
			}
		});
		var btnPageJump = $('<button />', {
			'class': "btn btn-default",
			'aria-controls': settings.sTableId,
			'tabindex': settings.iTabIndex
		}).html(lang.jump).on("click",function(){
			var curr = inputPageJump.val().replace(/\s|\D/g, '') | 0;
			if (curr) {
				var pages = api.page.info().pages;
				curr = curr > pages ? pages : curr;
				curr--;
				api.page(curr).draw(false);
			}
		});
	};
	
	var attachs = function( container, buttons ) {
		var i, ien, node, button;
		var clickHandler = function (e) {
			e.preventDefault();
			if (!$(e.currentTarget).hasClass('disabled')&&api.page() != e.data.action){
				api.page( e.data.action ).draw('page');
			}
		};
		for ( i=0, ien=buttons.length ; i<ien ; i++ ) {
			button = buttons[i];
			if ($.isArray( button )) {
				attachs(container,button);
			} else {
				btnDisplay = '';
				btnClass = '';
				switch ( button ) {
					case 'ellipsis':
						btnDisplay = '&#x2026;';
						btnClass = 'disabled';
						break;
					case 'first':
						btnDisplay = lang.sFirst;
						btnClass = button + (page > 0 ? '' : ' disabled');
						break;
					case 'previous':
						btnDisplay = lang.sPrevious;
						btnClass = button + (page > 0 ? '' : ' disabled');
						break;
					case 'next':
						btnDisplay = lang.sNext;
						btnClass = button + (page < pages-1 ? '' : ' disabled');
						break;
					case 'last':
						btnDisplay = lang.sLast;
						btnClass = button + (page < pages-1 ? '' : ' disabled');
						break;
					default:
						btnDisplay = button + 1;
						btnClass = page === button ? 'active' : '';
						break;
				}

				if (btnDisplay) {
					node = $('<li>',{
							'class': classes.sPageButton+' '+btnClass,
							'id': idx === 0 && typeof button === 'string'?settings.sTableId +'_'+ button : null
					}).append($('<a>', {
							'href': '#',
							'aria-controls': settings.sTableId,
							'aria-label': aria[button],
							'data-dt-idx': counter,
							'tabindex': settings.iTabIndex
					}).html(btnDisplay)
					).appendTo(container);
					settings.oApi._fnBindAction(
						node, {action: button}, clickHandler
					);
					counter++;
				}
			}
		}

		/*添加跳页功能*/
		var inputPageJump = $('<input>', {
			'type': "number",
			'min': 1,
			'max': pages,
			'class': "form-control"
		}).on("keyup",function(event){
			if (event.keyCode == 13) {
				var curr = this.value.replace(/\s|\D/g, '') | 0;
				if (curr) {
					var pages = api.page.info().pages;
					curr = curr > pages ? pages : curr;
					curr--;
					api.page(curr).draw(false);
				}
			}
		});
		var btnPageJump = $('<button />', {
			'class': "btn btn-default",
			'aria-controls': settings.sTableId,
			'tabindex': settings.iTabIndex
		}).html(lang.jump).on("click",function(){
			var curr = inputPageJump.val().replace(/\s|\D/g, '') | 0;
			if (curr) {
				var pages = api.page.info().pages;
				curr = curr > pages ? pages : curr;
				curr--;
				api.page(curr).draw(false);
			}
		});
		var spanPageJump = $('<span />', {
			'class': "input-group-btn"
		}).append(btnPageJump);
		$(host).prepend($('<div/>',{'class':"page_jump input-group"}).append(inputPageJump).append(spanPageJump));
	};
	
	var activeEl;
	
	activeEl = $(host).find(document.activeElement).data('dt-idx');

	attach($(host).empty().html('<ul class="pagination"/>').children('ul'),buttons);
	if(activeEl){
		$(host).find( '[data-dt-idx='+activeEl+']' ).focus();
	}
};


return DataTable;
}));
