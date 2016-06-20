package com.android.me.boardapp;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandler extends DefaultHandler{
	ArrayList<Board> list;
	Board board;

	//현재 실행부가 이 변수를 지나갓는지 여부 판단
	boolean board_id;
	boolean title;
	boolean writer;
	boolean content;
	boolean regdate;
	boolean hit;

	public void startDocument() throws SAXException {
		list= new ArrayList<Board>();
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.print("<"+qName+">");

		if(qName.equalsIgnoreCase("board")){
			board = new Board();
		}else if(qName.equalsIgnoreCase("board_id")){
			board_id=true; //지금 막 목격햇어!!!
		}else if(qName.equalsIgnoreCase("title")){
			title=true;
		}else if(qName.equalsIgnoreCase("writer")){
			writer=true;
		}else if(qName.equalsIgnoreCase("content")){
			content=true;
		}else if(qName.equalsIgnoreCase("regdate")){
			regdate=true;
		}else if(qName.equalsIgnoreCase("hit")){
			hit=true;
		}
	}
	public void characters(char[] ch, int start, int length) throws SAXException {
		String data=new String(ch, start, length);

		if(board_id){
			board.setBoard_id( Integer.parseInt(data));
			board_id=false; //다시 목격 않한걸로 돌려놓음!!
		}else if(title){
			board.setTitle(data);
			title=false;
		}else if(writer){
			board.setWriter(data);
			writer=false;
		}else if(content){
			board.setContent(data);
			content=false;
		}else if(regdate){
			board.setRegdate(data);
			regdate=false;
		}else if(hit){
			board.setHit(Integer.parseInt(data));
			hit=false;
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		//하나의 dto 를 list에 담자!!
		if(qName.equalsIgnoreCase("board")){
			list.add(board);
		}
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("list에 담겨진 게시물의 총 갯수는 "+list.size());
	}
}
