package org.esblink.module.loginmgmt.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 主机A向主机B发送“UDP－NetBIOS－NS”询问包，即向主机B的137端口，发Query包来询问主机B的NetBIOS Names信息。
 * 其次，主机B接收到“UDP－NetBIOS－NS”询问包，假设主机B正确安装了NetBIOS服务........... 而且137端口开放，则主机B会向主机A发送一个“UDP－NetBIOS－NS”应答包，即发Answer包给主机A。
 * 并利用UDP(NetBIOS Name Service)来快速获取远程主机MAC地址的方法
 *
 */
public class UdpGetClientMacAddr {
	 private static final Log log = LogFactory.getLog(UdpGetClientMacAddr.class); 
    private String         sRemoteAddr;
    private int            iRemotePort = 137;
    private byte[]         buffer      = new byte[1024];
    private DatagramSocket ds          = null;
    private String hostName;    

    public UdpGetClientMacAddr(String strAddr) throws Exception {
        sRemoteAddr = strAddr;
        ds = new DatagramSocket();
    }

    protected final DatagramPacket send(final byte[] bytes) throws IOException {
    	InetAddress address = InetAddress.getByName(sRemoteAddr);
    	this.hostName=address.getHostName();
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length, address, iRemotePort);
        ds.send(dp);
        return dp;
    }

    protected final DatagramPacket receive() throws Exception {
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
        ds.receive(dp);
        return dp;
    }

    // 询问包结构:
    // Transaction ID 两字节（16位） 0x00 0x00
    // Flags 两字节（16位） 0x00 0x10
    // Questions 两字节（16位） 0x00 0x01
    // AnswerRRs 两字节（16位） 0x00 0x00
    // AuthorityRRs 两字节（16位） 0x00 0x00
    // AdditionalRRs 两字节（16位） 0x00 0x00
    // Name:array [1..34] 0x20 0x43 0x4B 0x41(30个) 0x00 ;
    // Type:NBSTAT 两字节 0x00 0x21
    // Class:INET 两字节（16位）0x00 0x01
    protected byte[] getQueryCmd() throws Exception {
        byte[] t_ns = new byte[50];
        t_ns[0] = 0x00;
        t_ns[1] = 0x00;
        t_ns[2] = 0x00;
        t_ns[3] = 0x10;
        t_ns[4] = 0x00;
        t_ns[5] = 0x01;
        t_ns[6] = 0x00;
        t_ns[7] = 0x00;
        t_ns[8] = 0x00;
        t_ns[9] = 0x00;
        t_ns[10] = 0x00;
        t_ns[11] = 0x00;
        t_ns[12] = 0x20;
        t_ns[13] = 0x43;
        t_ns[14] = 0x4B;

        for (int i = 15; i < 45; i++) {
            t_ns[i] = 0x41;
        }

        t_ns[45] = 0x00;
        t_ns[46] = 0x00;
        t_ns[47] = 0x21;
        t_ns[48] = 0x00;
        t_ns[49] = 0x01;
        return t_ns;
    }

    // 表1 “UDP－NetBIOS－NS”应答包的结构及主要字段一览表
    // 序号 字段名 长度
    // 1 Transaction ID 两字节（16位）
    // 2 Flags 两字节（16位）
    // 3 Questions 两字节（16位）
    // 4 AnswerRRs 两字节（16位）
    // 5 AuthorityRRs 两字节（16位）
    // 6 AdditionalRRs 两字节（16位）
    // 7 Name<Workstation/Redirector> 34字节（272位）
    // 8 Type:NBSTAT 两字节（16位）
    // 9 Class:INET 两字节（16位）
    // 10 Time To Live 四字节（32位）
    // 11 Length 两字节（16位）
    // 12 Number of name 一个字节（8位）
    // NetBIOS Name Info 18×Number Of Name字节
    // Unit ID 6字节（48位

    protected final String getMacAddr(byte[] brevdata) throws Exception {
        // 获取计算机名
//    	this.hostName = new String(brevdata, 57, 18);
       log.debug(this.hostName);
       log.debug(new String(brevdata, 75, 18));
       log.debug(new String(brevdata, 93, 18));
        int i = brevdata[56] * 18 + 56;
        String sAddr = "";
        StringBuffer sb = new StringBuffer(17);
        // 先从第56字节位置，读出Number Of Names（NetBIOS名字的个数，其中每个NetBIOS Names Info部分占18个字节）
        // 然后可计算出“Unit ID”字段的位置＝56＋Number Of Names×18，最后从该位置起连续读取6个字节，就是目的主机的MAC地址。
        for (int j = 1; j < 7; j++) {
            sAddr = Integer.toHexString(0xFF & brevdata[i + j]);
            if (sAddr.length() < 2) {
                sb.append(0);
            }
            sb.append(sAddr.toUpperCase());
            if (j < 6) sb.append(':');
        }
        return sb.toString();
    }

    public final void close() {
        try {
            ds.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    

    public String getHostName() {
		return hostName;
	}

	public final String getRemoteMacAddr() throws Exception {
        byte[] bqcmd = getQueryCmd();
        send(bqcmd);
        DatagramPacket dp = receive();
        String smac = getMacAddr(dp.getData());
        close();
        return smac;
    }

    public static void main(String[] args) throws Exception {
        UdpGetClientMacAddr addr = new UdpGetClientMacAddr("10.88.1.63");
        System.out.println(addr.getRemoteMacAddr());
    }
}

 

